package com.example.telephonedirectory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.telephonedirectory.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewContacts;
    private ArrayList<String> contactList;
    private CustomArrayAdapter adapter;
    private Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewContacts = findViewById(R.id.listViewContacts);
        btnAddContact = findViewById(R.id.btnAddContact);

        contactList = new ArrayList<>();
        adapter = new CustomArrayAdapter(this, R.layout.list_item_contact, contactList);
        listViewContacts.setAdapter(adapter);

        // "Kişi Ekle" butonuna tıklama olayını ayarla
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog(v);
            }
        });
    }


    public void showAddContactDialog(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_contact_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTxtName = dialogView.findViewById(R.id.editTxtName);
        final EditText editTxtSurname = dialogView.findViewById(R.id.editTxtSurname);
        Button btnSaveContact = dialogView.findViewById(R.id.btnSaveContact);

        final AlertDialog alertDialog = dialogBuilder.create();

        // Kişi Ekle butonuna tıklanınca çalışacak işlem
        btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTxtName.getText().toString();
                String surname = editTxtSurname.getText().toString();

                if (!name.isEmpty() && !surname.isEmpty()) {
                    String contact = name + " " + surname;
                    contactList.add(contact); // Kişiyi listeye ekleyin
                    adapter.notifyDataSetChanged(); // Listeyi güncelleyin
                    alertDialog.dismiss(); // İletişim kutusunu kapat
                }
            }
        });

        alertDialog.show();
    }

    // CustomArrayAdapter sınıfı
    private class CustomArrayAdapter extends ArrayAdapter<String> {

        private int layoutResource;
        private ArrayList<String> contactList;

        public CustomArrayAdapter(MainActivity context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            layoutResource = resource;
            contactList = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            }

            TextView txtContact = convertView.findViewById(R.id.txtContact);
            ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

            String contact = getItem(position);
            txtContact.setText(contact);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactList.remove(position); // Kişiyi listeden kaldır
                    notifyDataSetChanged(); // Listeyi güncelle
                }
            });

            return convertView;
        }
    }

    public void onSaveContactClick(View view) {
        // Kişi ekleme işlemi tamamlandığında aşağıdaki satırları ekleyin:
        btnAddContact.setVisibility(View.VISIBLE); // "Kişi Ekle" butonunu görünür yap
        listViewContacts.setVisibility(View.VISIBLE); // Liste görünür yap
    }

}