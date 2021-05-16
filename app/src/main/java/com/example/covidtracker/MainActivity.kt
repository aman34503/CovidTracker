package com.example.covidtracker

import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {
        lateinit var WorldCasesTV: TextView
        lateinit var WorldRecoveredTV: TextView
        lateinit var WorldDeathsTV: TextView
        lateinit var CountryCasesTV: TextView
        lateinit var CountryRecoveredTV: TextView
        lateinit var CountryDeathsTV: TextView
        lateinit var stateRV: RecyclerView
        lateinit var StateRVAdapter: StateRVAdapter
        lateinit var StateList: List<StateModel>


        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)
                WorldCasesTV = findViewById(R.id.idTVWorldCases)
                WorldRecoveredTV = findViewById(R.id.idTVWorldRecovered)
                WorldDeathsTV = findViewById(R.id.idTVWorldDeaths)
                CountryCasesTV = findViewById(R.id.idTVIndiaCases)
                CountryDeathsTV = findViewById(R.id.idTVDeaths)
                CountryRecoveredTV = findViewById(R.id.idTVIndiaRecovered)
                stateRV = findViewById(R.id.idRVStates)
                StateList = ArrayList<StateModel>()
                getStateInfo()
                getWorldInfo()
        }

        private fun getStateInfo() {
                val url = " https://api.rootnet.in/covid19-in/stats/latest "
                val queue = Volley.newRequestQueue(this@MainActivity)
                val request =
                        JsonObjectRequest(Request.Method.GET, url, null, { response ->
                                try {
                                        val dataobj = response.getJSONObject("data")
                                        val summaryobj = dataobj.getJSONObject("summary")
                                        val cases: Int = summaryobj.getInt("total")
                                        val recovered: Int = summaryobj.getInt("discharged")
                                        val deaths: Int = summaryobj.getInt("deaths")

                                        CountryCasesTV.text = cases.toString()
                                        CountryRecoveredTV.text = recovered.toString()
                                        CountryDeathsTV.text = deaths.toString()

                                        val regionalArray = dataobj.getJSONArray("regional")
                                        for (i in 0 until regionalArray.length()) {
                                                val regionalObj = regionalArray.getJSONObject(i)
                                                val stateName: String = regionalObj.getString("loc")
                                                val cases: Int =
                                                        regionalObj.getInt("total Confirmed")
                                                val deaths: Int = regionalObj.getInt("deaths")
                                                val recovered: Int =
                                                        regionalObj.getInt("discharged")

                                                val stateModel = StateModel(
                                                        stateName,
                                                        recovered,
                                                        deaths,
                                                        cases
                                                )
                                                StateList = StateList + stateModel
                                        }
                                        StateRVAdapter = StateRVAdapter(StateList)
                                        stateRV.layoutManager = LinearLayoutManager(this)
                                        stateRV.adapter = StateRVAdapter

                                } catch (e: JSONException) {
                                        e.printStackTrace()
                                }
                        }, {
                                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
                        })
                queue.add(request)
        }


        private fun getWorldInfo() {
                val url = " https://corona.lmao.ninja/v3/covid-19/all "
                val queue = Volley.newRequestQueue(this@MainActivity)
                val request =
                        JsonObjectRequest(Request.Method.GET, url, null, { response ->
                                try {
                                        val worldCases: Int = response.getInt("cases")
                                        val worldRecovered: Int = response.getInt("recovered")
                                        val worldDeaths: Int = response.getInt("deaths")

                                        WorldRecoveredTV.text = worldRecovered.toString()
                                        WorldCasesTV.text = worldCases.toString()
                                        WorldDeathsTV.text = worldDeaths.toString()

                                } catch (e: JSONException) {
                                        e.printStackTrace()
                                }
                        }, { error ->
                                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()

                        })
        }
}