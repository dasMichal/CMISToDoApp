package com.example.cmistodoapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
	private final OnCreateContextMenuListener contextMenueListener;
	private final View.OnCreateContextMenuListener viewContextList;


	public FolderAdapter(List<ToDoFolder_Entity> folderliveData, OnItemClickListener listener, OnCreateContextMenuListener contextMenueListener1, View.OnCreateContextMenuListener viewContextList1)
	{
		this.folderData = folderliveData;
		this.listener = listener;
		this.contextMenueListener = contextMenueListener1;

		this.viewContextList = viewContextList1;
	}


	public interface OnItemClickListener
	{
		void onFolderClick(int id, Context context);

	}

	public interface OnCreateContextMenuListener
	{

		void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, int position);

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
		holder.folderCard.setTag(" " + this.folderData.get(position));



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


		public ViewHolder(@NonNull View view)
		{
			super(view);

			this.folderText = view.findViewById(R.id.folderText);
			this.folderCard = view.findViewById(R.id.folderCard);


			view.setOnCreateContextMenuListener(viewContextList);

			/*
			folderCard.setOnClickListener(v ->
			{
				Context context = view.getContext();
				listener.onFolderClick(folderCard.getId(),context);
			});


			 */


			//folderCard.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) contextMenueListener);
			//folderText.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) contextMenueListener);


			/*

			folderCard.setOnCreateContextMenuListener((menu, v, menuInfo) ->
			{


				onCreateContextMenu(menu,  view,  menuInfo,getAdapterPosition());



			});
			folderText.setOnCreateContextMenuListener((menu, v, menuInfo) ->
			{
				onCreateContextMenu(menu,  view,  menuInfo,getAdapterPosition());


			});



			 */

			view.setOnClickListener(this);

			//contextClickListener.onContextClick()


		}

		@Override
		public void onClick(View view)
		{

			Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.folderText.getText(), Toast.LENGTH_SHORT).show();


			Context context = view.getContext();

			listener.onFolderClick(folderCard.getId(), context);


		}


		//@Override
		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo, int adapterPosition)
		{

			contextMenueListener.onCreateContextMenu(menu, v, menuInfo, getAdapterPosition());
			System.out.println("LOL");

			//MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
			//MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");

			//Edit.setOnMenuItemClickListener(onEditMenu);
			//Delete.setOnMenuItemClickListener(onEditMenu);


		}


		private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{

				switch (item.getItemId())
				{
					case 1:
						//Do stuff
						break;

					case 2:
						//Do stuff

						break;
				}
				return true;
			}
		};


	}


}