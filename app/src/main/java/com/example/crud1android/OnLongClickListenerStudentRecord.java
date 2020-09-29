package com.example.crud1android;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClickListenerStudentRecord implements View.OnLongClickListener {
    Context context;
    String id;
    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();
        final CharSequence[] items = { "Editar", "Eliminar" };

        new AlertDialog.Builder(context).setTitle("Registros")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }
                        else if (item == 1) {

                            boolean deleteSuccessful = new TableControllerStudent(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "No se pudo Eliminar", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }
                        dialog.dismiss();

                    }
                }).show();
        return false;
    }
    public void editRecord(final int studentId) {
        final TableControllerStudent tableControllerStudent = new TableControllerStudent(context);
        ObjectStudent objectStudent = tableControllerStudent.readSingleRecord(studentId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);
        final EditText editTextStudentFirstname = (EditText) formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText editTextStudentEmail = (EditText) formElementsView.findViewById(R.id.editTextStudentEmail);
        editTextStudentFirstname.setText(objectStudent.firstname);
        editTextStudentEmail.setText(objectStudent.email);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Editar Registro")
                .setPositiveButton("Guardar Cambios",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ObjectStudent objectStudent = new ObjectStudent();
                                objectStudent.id = studentId;
                                objectStudent.firstname = editTextStudentFirstname.getText().toString();
                                objectStudent.email = editTextStudentEmail.getText().toString();
                                dialog.cancel();
                                boolean updateSuccessful = tableControllerStudent.update(objectStudent);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Registro Actualizado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                                }
                                ((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();
                            }

                        }).show();
    }

}