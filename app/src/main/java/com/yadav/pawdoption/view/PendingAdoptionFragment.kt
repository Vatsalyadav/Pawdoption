package com.yadav.pawdoption.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadav.pawdoption.adapter.PendingAdoptionViewAdapter
import com.yadav.pawdoption.dao.PendingAdoptionDAO
import com.yadav.pawdoption.databinding.FragmentPendingAdoptionsBinding
import com.yadav.pawdoption.model.PendingAdoption

class PendingAdoptionFragment : Fragment() {

    var _binding: FragmentPendingAdoptionsBinding? = null

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_pending_adoptions, container, false)

        val pendingAdoptionDAO: PendingAdoptionDAO = PendingAdoptionDAO();

        val pendingAdoptionList: MutableList<PendingAdoption> = pendingAdoptionDAO.getAdoptionList("1");

        val notesAdapter: RecyclerView.Adapter<PendingAdoptionViewAdapter.ViewHolder> = PendingAdoptionViewAdapter(pendingAdoptionList);



        _binding = FragmentPendingAdoptionsBinding.inflate(inflater, container, false)

        _binding?.apply {
            rvPendingAdoptions.apply{
                layoutManager= LinearLayoutManager(requireActivity())
                adapter=notesAdapter
            }
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // to navigate to the second fragment while click on the create note button (floating button)
//        _binding?.button?.setOnClickListener {
//            findNavController().navigate(R.id.action_pendingAdoptionFragment_to_confirmAdoptionFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}