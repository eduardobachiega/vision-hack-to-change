package br.com.vision.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import br.com.vision.adapter.ChatRecyclerAdapter;
import br.com.vision.model.Chat;
import butterknife.BindView;
import butterknife.ButterKnife;
import br.com.vision.R;
import butterknife.OnClick;


public class ChatBotFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    AIService aiService;

    @BindView(R.id.rvMessages)
    RecyclerView rvMessages;

    @BindView(R.id.etMessage)
    EditText etMessage;

    List<Chat> messages = new ArrayList<>();
    ChatRecyclerAdapter adapter;

    public ChatBotFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);
        final AIConfiguration config = new AIConfiguration("33d88a4e106a4bc0b03cd16eb67e8530",
                AIConfiguration.SupportedLanguages.PortugueseBrazil,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);
        ButterKnife.bind(this, view);
        adapter = new ChatRecyclerAdapter(messages);
        rvMessages.setAdapter(adapter);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        ((FloatingActionButton) getActivity().findViewById(R.id.fab)).hide();
        messages.add(new Chat("Já cadastrou seus gastos hoje?", true));
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

    @OnClick(R.id.btnSend)
    public void sendMessage() {
        final String message = etMessage.getText().toString();
        messages.add(new Chat(message, false));
        etMessage.setText("");
        adapter.notifyDataSetChanged();
        new AsyncTask<Void, Void, AIResponse>() {


            @Override
            protected AIResponse doInBackground(Void... voids) {
                try {
                    AIRequest aiRequest = new AIRequest();
                    aiRequest.setQuery(message);
                    aiService.textRequest(aiRequest);
                    final AIResponse aiResponse = aiService.textRequest(aiRequest);
                    return aiResponse;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;

            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                super.onPostExecute(aiResponse);
                if (aiResponse != null) {
                    messages.add(new Chat(aiResponse.getResult().getFulfillment().getSpeech(), true));
                    adapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
