package br.com.vision.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.vision.R;
import br.com.vision.adapter.AchievementsRecyclerAdapter;
import br.com.vision.model.Achievement;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AchievementsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.rvAchievements)
    RecyclerView rvAchievements;

    public AchievementsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        ButterKnife.bind(this, view);
        List<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement(true, "Pagar uma parcela atrasada", "10% de desconto na troca de óleo nos postos Brasil", "1/1"));
        achievements.add(new Achievement(true, "Pagar cinco parcelas atrasadas", "50% de desconto na próxima troca de pneus na rede PneuToppen", "5/5"));
        achievements.add(new Achievement(false, "Pagar metade das parcelas atrasadas", "Reduzir número de ligações de cobrança em até 25%", "15/20"));
        achievements.add(new Achievement(false, "Pagar 75% das parcelas atrasadas", "Parar de receber ligações de cobrança", "15/30"));
        achievements.add(new Achievement(false, "Pagar duas parcelas atrasadas no mesmo mês", "Troca de óleo grátis", "0/1"));
        achievements.add(new Achievement(false, "Pagar três parcelas atrasadas no mesmo mês", "Cambagem grátis", "0/3"));
        achievements.add(new Achievement(false, "Quitar sua dívida", "Jogo de calotas gratuito", "0/1"));
        AchievementsRecyclerAdapter adapter = new AchievementsRecyclerAdapter(achievements);
        rvAchievements.setAdapter(adapter);
        rvAchievements.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
