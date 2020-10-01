package com.example.ensi;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<Class> Classes;
    private mClickListener listener;

    public MyAdapter(ArrayList<Class> classes, mClickListener listener) {
        this.Classes = classes;
        this.listener = listener;
    }

    // lorsque on a crée la classe MyAdapter qui sert à l'adaption des données
    //au "recyclerview" et la determination du "view holder" pour l'affichage de ces données,
    //3 overrides methodes sont automatiquement crées

    //onCreateViewHolder(ViewGroup parent, int viewType)
    //onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    //getItemCount()


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //créer le subclasse viewHolder qui implemente l'interface OnClickListener dans la classe View
                                                                        // cet inerface a la methode onClick(View v) qui est appelé lorsque the view est cliqué. au niveau de ce methode, the listener is set avec la methode de l'interface monClick
        public TextView mTextView;
        public ImageView Remove;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textview);  // l'acces des views (textview et imageview) via les id determinés en XML
            Remove = itemView.findViewById(R.id.OptionsMenu);
            itemView.setOnClickListener(this);  //set the onclicklistenr to the class layout ==> the whole item itself in the adapter is then clickable

        }

        @Override
        public void onClick(View view) {  //(abstract void : Called when a view has been clicked)
            listener.monClick(view, getAdapterPosition());  //calling the interface’s function
                                                            //monclick est appelé par l'android framework lorsque le view passé en parametre de position du viewholder est cliqué

        }
    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        //c'st la methode qui sert à initialiser les "view holders"
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Cette méthode est appelée pour chaque ViewHolder et la lie (bind) à l'adapter (thus the name duh)
        // C'est là où nous transmettrons nos données à notre ViewHolder, ici c'est le cardview
        //le cardview contient 2 elements : textview pour l'affichage du donnée deja ecrit ET imageview pour le menu pour la suppression des données

        final Class obj = Classes.get(position); //créer un objet de type classe pour chaque cardview de position "position"
        holder.mTextView.setText(obj.getClasse_name()); //set le nom du classe au textview
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Classes.remove(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
                Toast.makeText(view.getContext(), "deleted", Toast.LENGTH_SHORT).show();


            }
        });


    }


    @Override
    public int getItemCount() {
            return Classes.size();  }  // retourner la taille des classes à afficher (nombre de cardviews)

    public interface mClickListener {
        void monClick(View v, int position);
    }


    //Contairement au listview, Le recyclerview n'a pas de setOnItemClickListener pour passer à un autre activité
    // nous devons donc créer notre propre façon de le faire et c'est par l'implémentation de notre interface mClickListener dans l'adapter avec un SEULE méthode de rappel monclick(callback method)
    //il faut passer l'interface listener (de type mClickListener) comme paramétre au constructeur



}