package com.example.cmistodoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmistodoapp.R;
import com.example.cmistodoapp.persistency.ToDoFolder_Entity;

import java.util.List;




public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>
{
	private final List<ToDoFolder_Entity> folderData;
	private final OnItemClickListener listener;


	public FolderAdapter(List<ToDoFolder_Entity> folderliveData, OnItemClickListener listener)
	{
		this.folderData = folderliveData;
		this.listener = listener;
	}

	public interface OnItemClickListener
	{
		void onFolderClick(int id, Context context);

	}



	@Override
	public FolderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_card, parent, false);
		return new ViewHolder(rowItem);
	}



	@Override
	public void onBindViewHolder(FolderAdapter.ViewHolder holder, int position)
	{

		holder.folderText.setText(this.folderData.get(position).getFolderName());
		holder.folderCard.setId(this.folderData.get(position).getFolderID());

		holder.folderCard.setTag(this.folderData.get(position).getFolderID());


		//holder.folderText.setText(" "+this.folderData.get(position));

		//holder.folderCard.setId(this.folderData.size());
		holder.folderCard.setTag(" "+this.folderData.get(position));

		//holder.bind(data.get(position), listener);

		//holder.itemView.setSelected(selectedPos == position);
		//holder.memCard.setSelected(selectedPos == position);

	}

	@Override
	public int getItemCount()
	{
		return this.folderData.size();
	}


	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private final TextView folderText;
		private final CardView folderCard;
		

		public ViewHolder(@NonNull View view) {
			super(view);

			this.folderText = view.findViewById(R.id.folderText);
			this.folderCard = view.findViewById(R.id.folderCard);



			folderCard.setOnClickListener(v ->
			{

				Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.folderText.getText(), Toast.LENGTH_SHORT).show();

				System.out.println("Click");
				Context context = view.getContext();
				listener.onFolderClick(folderCard.getId(),context);

			});






			view.setOnClickListener(this);


		}

		@Override
		public void onClick(View view)
		{

			Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.folderText.getText(), Toast.LENGTH_SHORT).show();

			Context context = view.getContext();

			listener.onFolderClick(folderCard.getId(),context);





		}


	}


}

