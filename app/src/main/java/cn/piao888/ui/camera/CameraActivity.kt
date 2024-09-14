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
    val adapter by lazy { CameraAdapter() }


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
    }

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

    private fun onListener() {
        photograph.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }


    private fun dispatchTakePictureIntent() {
        val photoFile: File?
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
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            } else {
                Toast.makeText(this, "没有可用的相机应用", Toast.LENGTH_SHORT).show()
            }
        }
    }


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
}
