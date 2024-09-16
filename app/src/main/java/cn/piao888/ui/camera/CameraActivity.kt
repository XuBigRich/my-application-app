package cn.piao888.ui.camera;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraDevice
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.piao888.R
import cn.piao888.adapter.CameraAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class CameraActivity : AppCompatActivity() {
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    val photograph by lazy { findViewById<Button>(R.id.photograph) }
    val photograph2 by lazy { findViewById<Button>(R.id.photograph2) }
    val adapter by lazy { CameraAdapter() }
    val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // 照片拍摄成功，返回首页并携带图片 URI
                returnToHomeWithImage()
            }
        }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 处理新接收到的 Intent 数据
        val imageUri = intent?.getStringExtra("imageUri")
        imageUri?.let {
            adapter.datas.add(Uri.parse(imageUri))
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_layout)
        initData()
        requirePermission()
        onListener()
    }

    companion object {
        val CAMERA_REQUEST_CODE = 100
        val STORAGE_REQUEST_CODE = 200
        val REQUEST_IMAGE_CAPTURE = 1
        var photoURI: Uri? = null
    }

    private fun initData() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // 注册用于拍照的 ActivityResultLauncher
    }

    //请求相机权限
    private fun requirePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            );
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                STORAGE_REQUEST_CODE
            );
        }
    }

    //添加按钮 监听器 调用启动相机
    private fun onListener() {
        photograph.setOnClickListener {
            dispatchTakePictureIntent()
        }
        photograph2.setOnClickListener {
            photoURI = FileProvider.getUriForFile(
                this,
                "cn.piao888.myapplication",
                createImageFile()
            )
            takePictureLauncher.launch(photoURI)
        }
    }

    //启动相机
    private fun dispatchTakePictureIntent() {
        val photoFile: File
        try {
            photoFile = createImageFile()  // 创建图片文件
        } catch (ex: IOException) {
            ex.printStackTrace() // 或者使用 Log.e() 记录错误
            Toast.makeText(this, "无法创建文件，请重试", Toast.LENGTH_SHORT).show()
            return  // 如果创建文件失败，直接返回
        }

        photoFile.let {
            // 使用 FileProvider 共享文件给相机应用
            photoURI = FileProvider.getUriForFile(this, "cn.piao888.myapplication", it)
            // 创建 Intent 并启动相机
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoURI) // 将 Uri 传递给相机
                flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION // 授予相机应用写入权限
            }

            // 确保有相机应用可处理该 Intent
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "没有可用的相机应用", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //创建图片地址
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //私有存储 照片只属于当前应用
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //公共外部存储 文件属于全体应用
//        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    //相机回调·页面
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoURI?.let {
                // 添加图片到列表
                adapter.datas.add(it)
                // 通知 RecyclerView 刷新
                adapter.notifyDataSetChanged()
            }
        }
    }

    //相机回调·页面2
    private fun returnToHomeWithImage() {
        // 跳转回首页
        val homeIntent = Intent(this, CameraActivity::class.java).apply {
            // 将拍照的图片地址传递给首页
            putExtra("imageUri", photoURI.toString())
            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        }

        startActivity(homeIntent)
//        finish() // 结束当前 Activity
    }
}
