package com.tomasm.android01exam.fragment

import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewSwitcher

import com.tomasm.android01exam.R

class AvailableDishesFragment : Fragment() {

    //Enumerado con valores, en este caso números (0,1)
    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        AVAILABLE_DISHES(1)
    }

    companion object {

        fun newInstance(): AvailableDishesFragment {
            val fragment = AvailableDishesFragment()
            return fragment
        }
    }

    lateinit var rootView: View
    lateinit var viewSwitcher: ViewSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {

            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_available_dishes, container, false)

            viewSwitcher = rootView.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)

            viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index
            
        }

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}