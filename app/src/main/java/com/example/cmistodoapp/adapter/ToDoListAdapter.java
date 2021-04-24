package com.example.cmistodoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmistodoapp.R;
import com.example.cmistodoapp.persistency.ToDo_Entity;

import java.util.List;




public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder>
{
	private List<String> data;
	private List<ToDo_Entity> todoData;

	private final OnItemClickListener listener;

	public ToDoListAdapter(List<ToDo_Entity> todoLiveData, OnItemClickListener listener)
	{
		this.todoData = todoLiveData;
		this.listener = listener;
	}

	public interface OnItemClickListener
	{

		void onToDoClick(int id, Context context);

	}

	public void setData(List<ToDo_Entity> todoLiveData)
	{
		this.todoData = todoLiveData;
	}


	@Override
	public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card_layout, parent, false);
		return new ViewHolder(rowItem);
	}



	@Override
	public void onBindViewHolder(ToDoListAdapter.ViewHolder holder, int position)
	{
		holder.ToDoText.setText(this.todoData.get(position).getToDoName());
		holder.todo1.setId(this.todoData.get(position).getToDoID());
		//holder.ToDoChecked.setEnabled(this.todoData.get(position).isDone());
		holder.ToDoChecked.setChecked(this.todoData.get(position).isDone());
		//holder.bind(this.todoData.get(position),listener);

		/*
		holder.ToDoText.setText(" "+this.data.get(position));
		holder.ToDoChecked.setEnabled(true);

		holder.todo1.setId(this.data.size());
		holder.bind(data.get(position), listener);


		 */
	}

	@Override
	public int getItemCount()
	{
		//return this.data.size();
		return  this.todoData.size();
	}



	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private TextView ToDoText;
		private CheckBox ToDoChecked;
		private CardView todo1;



		public ViewHolder(@NonNull View view) {
			super(view);

			this.ToDoText = view.findViewById(R.id.todoTitle);
			this.ToDoChecked = view.findViewById(R.id.ToDoChecked);
			this.todo1 = view.findViewById(R.id.todo1);



			ToDoChecked.setOnClickListener(v ->
			{
				//Toast.makeText(this, "Checked : " + getLayoutPosition()+" "), Toast.LENGTH_SHORT).show();
				Toast.makeText(v.getContext(), "Checked : " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
			});



			view.setOnClickListener(this);


		}

		@Override
		public void onClick(View view)
		{


			Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.ToDoText.getText(), Toast.LENGTH_SHORT).show();
			Context context = view.getContext();

			listener.onToDoClick(todo1.getId(),context);


			/*
			Intent intent = new Intent(context, ToDoEdit_Create.class);
			intent.putExtra("toDoTitle",ToDoText.getText());
			intent.putExtra("toDoID",todo1.getId());
			context.startActivity(intent);
			*/
		}


		public void bind(String integer, OnItemClickListener listener)
		{
			ToDoChecked.setOnClickListener(v -> {
				if (ToDoChecked.isChecked())
				{


					System.out.println(data);
					//System.out.println(getLayoutPosition());
					System.out.println(data.get(getLayoutPosition()));
					//System.out.println(data.get(getAdapterPosition()));
					System.out.println(ToDoChecked.isChecked());

					//listener.onItemClick(integer);

					int curSize = getItemCount();




					Toast.makeText(v.getContext(), " "+integer.toString(), Toast.LENGTH_SHORT).show();



				} else
				{

					System.out.println(ToDoChecked.isChecked());

				}



			});
		}
	}


}

