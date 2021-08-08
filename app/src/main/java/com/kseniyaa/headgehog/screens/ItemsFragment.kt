package com.kseniyaa.headgehog.screens

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kseniyaa.headgehog.Api
import com.kseniyaa.headgehog.App
import com.kseniyaa.headgehog.ItemsAdapter
import com.kseniyaa.headgehog.model.Jokes
import com.kseniyaa.headgehog.model.Result
import com.kseniyaa.headgehog.model.ResultCount
import com.kseniyaa.headgehog.R
import kotlinx.android.synthetic.main.fragment_items.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ItemsFragment : Fragment() {

    private var api: Api? = null
    var adapter = ItemsAdapter()
    private var jokes: List<Jokes> = ArrayList()
    private var jokesCount: Int? = null
    private var items: Result? = null
    private var itemsCount: ResultCount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = (activity?.application as App).api
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loadJokesCount()
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.adapter = adapter

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        reload_btn.setOnClickListener {
            val enterCount = count_et.text.toString().toIntOrNull()
            if (jokesCount!! < count_et.text.toString().toIntOrNull()!!)
                Toast.makeText(activity, "Ты столько не вывезешь, давай поменьше", Toast.LENGTH_SHORT).show()
            else loadItems(enterCount)

            count_et.setText("")
        }

        count_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (count_et.text.toString() == "") reload_btn.isEnabled = false
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count_et.text.toString() != "") reload_btn.isEnabled = true
            }
        })
    }

    private fun loadItems(jokeCount: Int?) {
        val call = api?.getItems(jokeCount)

        call?.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                items = response.body()
                jokes = items!!.value
                items?.let { adapter.setJokes(jokes) }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                println(t)
            }
        })
    }

    private fun loadJokesCount() {
        val call = api?.getJokesCount()

        call?.enqueue(object : Callback<ResultCount> {
            override fun onResponse(call: Call<ResultCount>, response: Response<ResultCount>) {
                itemsCount = response.body()
                jokesCount = itemsCount!!.value
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ResultCount>, t: Throwable) {
                println(t)
            }
        })
    }

    val Any.TAG: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }
}



