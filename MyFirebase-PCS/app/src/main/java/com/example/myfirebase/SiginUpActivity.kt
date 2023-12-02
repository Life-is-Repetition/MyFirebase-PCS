package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SiginUpActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siginup)

        findViewById<Button>(R.id.login)?.setOnClickListener {
            val userEmail = findViewById<EditText>(R.id.useremail)?.text.toString()
            val password = findViewById<EditText>(R.id.password)?.text.toString()
            val username = findViewById<EditText>(R.id.username)?.text.toString()
            val userbirthdate = findViewById<EditText>(R.id.userbirthdate)?.text.toString()

            dosigin(userEmail, password,username,userbirthdate)
        }
    }


    private fun dosigin(userEmail: String, password: String, username: String, userbirthdate: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공 시, 추가 정보를 Firestore에 저장
                    val user = Firebase.auth.currentUser
                    val uid = user?.uid ?: ""
                    val db = FirebaseFirestore.getInstance()

                    // 'users' 컬렉션에 사용자 정보 추가
                    val userMap = hashMapOf(
                        "name" to username,
                        "birthdate" to userbirthdate
                    )

                    db.collection("users").document(uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w("SignUpActivity", "Error adding user information to Firestore", e)
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

}
}