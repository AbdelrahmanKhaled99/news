package com.example.news.ui.main.fragments.News.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.news.api.model.Article
import com.example.news.databinding.ItemNewsBinding

class ArticlesAdapter(var articles: List<Article?>) : Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    class ArticlesViewHolder(val binding: ItemNewsBinding) : ViewHolder(binding.root) {
        fun bind(article: Article?) {
            binding.apply {
                newsDateTv.text = article?.publishedAt
                newsTitleTv.text = article?.title
                newsSourceTv.text = article?.source?.name
                Glide.with(root).load(article?.urlToImage).into(binding.newsImg)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun update(newList: List<Article?>) {
        articles = newList
        notifyDataSetChanged()
    }
}

