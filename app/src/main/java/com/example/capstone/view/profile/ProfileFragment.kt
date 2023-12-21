package com.example.capstone.view.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.SettingPreferences
import com.example.capstone.data.api.response.NewPhotoResponse
import com.example.capstone.data.api.retrofit.ApiConfig
import com.example.capstone.data.api.retrofit.ApiService
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.reduceFileImage
import com.example.capstone.uriToFile
import com.example.capstone.view.landing.LandingActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var currentImageUri: Uri? = null
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var logoutButton: ImageView
    private var token: String? = null
    private val REQUEST_CODE_GALLERY = 1
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileIMG: ImageView = binding.profileImage

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.profile)

        setupAction()

        settingPreferences = SettingPreferences.getInstance(requireContext())
        lifecycleScope.launch {
            val userName = settingPreferences.getUserName().first()
            if (!userName.isNullOrBlank()) {
                binding.nameProfile.text = "$userName"
            }
            val email = settingPreferences.getEmail().first()
            if (!email.isNullOrBlank()) {
                binding.valueEmail.text = "$email"
            }
            val id = settingPreferences.getUserId().first()
            if (!id.isNullOrBlank()) {
                userId = id
            }
            val tokenData = settingPreferences.getToken().first()
            if (!tokenData.isNullOrBlank()) {
                token = tokenData
            }

            Log.d("userId", userId.toString() )
            Log.d("token", token.toString())
            try {
                val apiService: ApiService = ApiConfig.getApiService()
                val newPhotoResponse =
                    apiService.getUsersProfile(token.toString(), userId.toString())


                withContext(Dispatchers.Main) {
                    // Gunakan data dari NewPhotoResponse langsung
                    val imageUrl = newPhotoResponse.user?.profileImageUrl

                    // Use Glide to load the image with the obtained URL
                    context?.let {
                        Glide.with(it)
                            .load(imageUrl)
                            .into(profileIMG)
                    }
                }
            } catch (e: Throwable) {
                // Handle exceptions, termasuk HttpException jika diperlukan
            }
        }


        GlobalScope.launch(Dispatchers.IO) {

        }

        logoutButton = binding.actionLogout
        logoutButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout_confirm))
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton("OK") { _, _ ->
                    lifecycleScope.launch {
                        settingPreferences.clearDataStore()

                        val intent = Intent(requireContext(), LandingActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
                .setNegativeButton(getString(R.string.logout_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            uploadImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            data?.data?.let { selectedImageUri ->
                currentImageUri = selectedImageUri
                showSelectedImage(selectedImageUri)
            }
        }
    }

    private fun showSelectedImage(uri: Uri) {
        // Menggunakan Glide atau cara lainnya untuk menampilkan gambar dari URI ke ImageView
        Glide.with(requireContext())
            .load(uri)
            .into(binding.profileImage)
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")

            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())

//            if (fileExtension!= null) {
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
            Log.d("mimeType", mimeType.toString())
            val imageRequestBody = imageFile.asRequestBody("image/jpg".toMediaType())
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                imageRequestBody
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.uploadImage(imagePart, userId.toString())
                    handleSuccessResponse(successResponse)
                } catch (e: HttpException) {
                    handleErrorResponse(e)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun handleSuccessResponse(successResponse: NewPhotoResponse) {
        showToast(successResponse.message!!)
    }

    private fun handleErrorResponse(e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, NewPhotoResponse::class.java)
        showToast(errorResponse.message!!)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAction() {
        binding.settingLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

}

