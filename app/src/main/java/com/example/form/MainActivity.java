package com.example.form;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.form.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("applications");

        binding.viewDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewFormsData.class);
            startActivity(intent);
        });



        binding.submitButton.setOnClickListener(v -> {
            if (validateForm()) {
                submitForm();
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;


        String firstName = binding.firstNameInput.getText().toString().trim();
        String lastName = binding.lastNameInput.getText().toString().trim();
        String email = binding.emailInput.getText().toString().trim();
        String country = binding.countryInput.getText().toString().trim();
        String phone = binding.phoneInput.getText().toString().trim();
        String postalCode = binding.postalInput.getText().toString().trim();


        if (firstName.isEmpty()) {
            binding.firstNameLayout.setError("First name is required");
            isValid = false;
        }
        if (lastName.isEmpty()) {
            binding.lastNameLayout.setError("Last name is required");
            isValid = false;
        }
        if (!isValidEmail(email)) {
            binding.emailLayout.setError("Valid email is required");
            isValid = false;
        }
        if (country.isEmpty()) {
            binding.countryLayout.setError("Country is required");
            isValid = false;
        }
        if (!isValidPhoneNumber(phone)) {
            binding.phoneLayout.setError("Valid phone number is required");
            isValid = false;
        }
        if (!isValidPostalCode(postalCode)) {
            binding.postalLayout.setError("Valid postal code is required");
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^\\d{10}$");
    }

    private boolean isValidPostalCode(String postalCode) {
        return postalCode.matches("^\\d{6}$");
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void submitForm() {
        HashMap<String, Object> formData = new HashMap<>();
        formData.put("firstName", binding.firstNameInput.getText().toString().trim());
        formData.put("lastName", binding.lastNameInput.getText().toString().trim());
        formData.put("email", binding.emailInput.getText().toString().trim());
        formData.put("address", binding.addressInput.getText().toString().trim());
        formData.put("city", binding.cityInput.getText().toString().trim());
        formData.put("region", binding.regionInput.getText().toString().trim());
        formData.put("postalCode", binding.postalInput.getText().toString().trim());
        formData.put("country", binding.countryInput.getText().toString().trim());
        formData.put("phone", binding.phoneInput.getText().toString().trim());
        formData.put("timestamp", ServerValue.TIMESTAMP);

        reference.push().setValue(formData)
                .addOnSuccessListener(aVoid -> {
                    clearForm();
                    showMessage("Application submitted successfully!");
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    showMessage("Error submitting application: " + e.getMessage());
                });
    }
    private void clearForm() {
        binding.firstNameInput.setText("");
        binding.lastNameInput.setText("");
        binding.emailInput.setText("");
        binding.addressInput.setText("");
        binding.cityInput.setText("");
        binding.regionInput.setText("");
        binding.postalInput.setText("");
        binding.countryInput.setText("");
        binding.phoneInput.setText("");
        
        binding.firstNameLayout.setError(null);
        binding.lastNameLayout.setError(null);
        binding.emailLayout.setError(null);
    }

    private void showMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
