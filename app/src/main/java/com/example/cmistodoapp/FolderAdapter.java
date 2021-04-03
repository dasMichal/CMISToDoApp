package com.example.cmistodoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;




public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>
{
	private final List<String> data;
	private final OnItemClickListener listener;
	private int selectedPos = RecyclerView.NO_POSITION;
	private int countFlipped = 0;

	public FolderAdapter(List<String> generateData, OnItemClickListener listener)
	{
		this.data = generateData;
		this.listener = listener;
	}

	public interface OnItemClickListener
	{
		void onItemClick(Integer item);
		void test(View v);

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

		holder.folderText.setText(" "+this.data.get(position));

		holder.folderCard.setId(this.data.size());
		holder.folderCard.setTag(" "+this.data.get(position));

		//holder.bind(data.get(position), listener);

		//holder.itemView.setSelected(selectedPos == position);
		//holder.memCard.setSelected(selectedPos == position);

	}

	@Override
	public int getItemCount()
	{
		return this.data.size();
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
				Intent intent = new Intent(context, ToDoList.class);
				intent.putExtra("selectedFolder", folderText.getText());

				context.startActivity(intent);






			});







			view.setOnClickListener(this);


		}

		@Override
		public void onClick(View view)
		{




			Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.folderText.getText(), Toast.LENGTH_SHORT).show();

			System.out.println("Click");
			Context context = view.getContext();
			Intent intent = new Intent(context, ToDoList.class);
			intent.putExtra("selectedFolder", folderText.getText());

			context.startActivity(intent);



		}


		/*
		public void bind(String integer, OnItemClickListener listener )
		{




		}

		 */
	}


}

