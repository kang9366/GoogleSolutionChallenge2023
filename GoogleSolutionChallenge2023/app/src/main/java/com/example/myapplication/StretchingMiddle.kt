package com.example.myapplication

import android.Manifest
import android.app.ActivityManager
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.SurfaceHolder
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.myapplication.databinding.StretchingMiddleBinding
import java.util.*
import kotlin.concurrent.schedule


class StretchingMiddle : AppCompatActivity() {

    //page_num_check : 현재의 페이지 수(page_num)을 받고 거기에 알맞는 스트레칭 설명문을 리턴
    fun page_num_check(page_num : Int): String{
        when (page_num){
            1 -> return getString(R.string.eyespin2_des)
            2 -> return getString(R.string.eyeupdown_des)
            3 -> return getString(R.string.eyeleftright_des)
            4 -> return getString(R.string.eyepush_des)
            5 -> return getString(R.string.eyeseefar_des)
            7 -> return getString(R.string.neckup_des)
            8 -> return getString(R.string.neckdown_des)
            9 -> return getString(R.string.neckleftright_des)
            // 1~5까지 눈 스트레칭 / 7~9까지는 목스트레칭 / 각각의 가장 처음 스트레칭 관련 코드는 아래 작성
        }
        return "ERROR"
    }
    private var page_num = 0

    private lateinit var binding : StretchingMiddleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StretchingMiddleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkCameraPermission()
        //인텐드가 EyeStretchingStart에서 오면 eyespin1이 옴 => 첫번째 눈 스트레칭 화면 작동, 그리고 다음 스트레칭이 나오도록 page_num을 1로 변화
        binding.nextButton.isVisible = false

        if(intent.hasExtra("eyespin1")){
            binding.stretchingDes.text = getString(R.string.eyespin1_des)
            page_num = 1
            Timer().schedule(5000) {

                runOnUiThread {
                    binding.nextButton.isVisible = true
                }
            }
        }//인텐드가 NeckStretchingStart에서 오면 neckforehead가 옴 => 첫번째 목 스트레칭 화면 작동, 그리고 다음 스트레칭이 나오도록 page_num을 7로 변화
        else if(intent.hasExtra("neckforehead")){
            binding.stretchingDes.text = getString(R.string.neckforehead_des)
            page_num = 7
            Timer().schedule(5000) {

                runOnUiThread {
                    binding.nextButton.isVisible = true
                }
            }
        }

        initNextButton()
    }

    override fun onPause() {
        super.onPause()
        val activityManager = applicationContext
            .getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(taskId, 0)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun checkCameraPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 카메라 실행 부분
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                
            } else {
                ActivityCompat.requestPermissions(this@StretchingMiddle, arrayOf<String?>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    //카메라 권한 설정
    private fun initNextButton(){
        binding.nextButton.setOnClickListener{
            if(page_num == 6 || page_num == 10){
                val intent = Intent(this, StretchingEnd::class.java)
                finish()
                startActivity(intent)
            }
            else{
                binding.nextButton.isVisible = false
                binding.stretchingDes.text = page_num_check(page_num)
                page_num++

                Timer().schedule(5000) {
                    runOnUiThread {
                        binding.nextButton.isVisible = true
                    }
                }
            }
        }
    }

}



