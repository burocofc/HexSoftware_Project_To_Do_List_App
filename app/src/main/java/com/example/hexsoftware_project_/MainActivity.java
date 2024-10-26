package com.example.hexsoftware_project_;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


// MainActivity class that extends AppCompatActivity to manage app's lifecycle and components
public class MainActivity extends AppCompatActivity {

    Button add;  // Button to trigger the dialog for adding new tasks
    AlertDialog dialog;  // Dialog for taking task input
    LinearLayout layout;  // Layout to contain dynamically added task views (cards)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Sets the layout for this activity

        add = findViewById(R.id.add);  // Initialize the "add" button
        layout = findViewById(R.id.container);  // Initialize the layout container for task cards

        buildDialog();  // Method call to build and configure the task input dialog
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();  // Show the dialog when the "add" button is clicked
            }
        });
    }

    // Method to build and configure the task input dialog
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);  // Inflate custom dialog layout

        final EditText name = view.findViewById(R.id.nameEdit);  // Input field to enter task name

        builder.setView(view);  // Set the custom view for the dialog
        builder.setTitle("Enter your task")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(name.getText().toString());  // Add task card with entered name
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action for "Cancel" button (does nothing here)
                    }
                });

        dialog = builder.create();  // Create the dialog from builder settings
    }

    // Method to add a new card (view) for each task in the layout container
    private void addCard(String name) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);  // Inflate a new card view

        TextView nameView = view.findViewById(R.id.name);  // TextView to display the task name on the card
        Button delete = view.findViewById(R.id.delete);  // Button to delete the task card

        nameView.setText(name);  // Set the task name to TextView
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmation(view, name);  // Remove the card view from layout when delete is clicked
            }
        });
        layout.addView(view);  // Add the card view to the layout container
    }

    // Show confirmation dialog for task deletion
    private void showDeleteConfirmation(View view, String taskName) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    layout.removeView(view);  // Remove the card view from layout
              })
                .setNegativeButton("No", null)
                .show();
    }
}
