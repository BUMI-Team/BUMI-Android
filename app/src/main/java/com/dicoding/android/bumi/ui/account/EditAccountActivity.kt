package com.dicoding.android.bumi.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.bumi.MainActivity
import com.dicoding.android.bumi.R
import com.dicoding.android.bumi.databinding.ActivityEditAccountBinding
import com.dicoding.android.bumi.utils.Constants
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference

class EditAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAccountBinding
    private lateinit var accViewModel: AccountViewModel

    private var PICK_IMAGE_REQUEST = 111
    private var filePath: Uri? = null

//    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
//    private val storageRef: StorageReference = storage.getReferenceFromUrl("gs://bumi-api-4e903.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action Bar
        supportActionBar?.title = "Edit Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        setupAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imgView = binding.ivAvatar

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                //getting image from gallery
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

                //Setting image to ImageView
                imgView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupViewModel() {
        accViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        accViewModel.getUser(Constants.token)
        accViewModel.setUser().observe(this) {
            if (it != null) {
                binding.apply {
                    val tvName = binding.tvEditNameAcc
                    val tvEmail = binding.tvEditEmailAcc

                    tvName.setText(it.userRecord.displayName)
                    tvEmail.setText(it.userRecord.email)
                }
            }
        }
    }

    private fun setupAction() {
        binding.btnChangePhoto.setOnClickListener {
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_PICK
//            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)

            Toast.makeText(this@EditAccountActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.btnConfirm.setOnClickListener {
            val tvName = binding.tvEditNameAcc
            val tvEmail = binding.tvEditEmailAcc

            // Photo
//            var pd: ProgressDialog? = null
//            pd = ProgressDialog(this@EditAccountActivity)
//            pd.setMessage("Uploading")
//            if (filePath != null) {
//                pd.show()
//                val childRef = storageRef.child("image.jpg")
//
//                //uploading the image
//                val uploadTask = childRef.putFile(filePath!!)
//                uploadTask.addOnSuccessListener {
//                    pd.dismiss()
//                    Toast.makeText(this@EditAccountActivity, "Upload berhasil", Toast.LENGTH_SHORT)
//                        .show()
//                }.addOnFailureListener { e ->
//                    pd.dismiss()
//                    Toast.makeText(this@EditAccountActivity, "Upload gagal -> $e", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            } else {
//                Toast.makeText(this@EditAccountActivity, "Pilih Gambar", Toast.LENGTH_SHORT).show()
//            }

            // Data User
            accViewModel.setUpdateUser(Constants.token, tvName.text.toString(), tvEmail.text.toString() )
            setupViewModel()

            AlertDialog.Builder(this@EditAccountActivity).apply {
                setTitle("Profil Berhasil di Update!")
                setMessage("Update berhasil, Have a nice day")
                setPositiveButton("Ok") { _, _ ->
                    setupViewModel()
                    val intent = Intent (this@EditAccountActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        }
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}