package com.example.justchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingDialogue {
      private  Activity activity;
      private  AlertDialog alertDialog;

        LoadingDialogue(Activity myactivity){
            activity=myactivity;
        }
        void startLoading(){
            AlertDialog.Builder builder=new AlertDialog.Builder(activity);
            LayoutInflater inflater=activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_dailog,null));
            builder.setCancelable(false);

            alertDialog=builder.create();
            alertDialog.show();
        }
        void dismissDialog(){
            alertDialog.dismiss();
        }
    }

