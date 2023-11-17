package com.example.nhom10_duan1.Frag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nhom10_duan1.LOPADAPTER.SanphamAdapter;
import com.example.nhom10_duan1.LOPDAO.HangDao;
import com.example.nhom10_duan1.LOPDAO.SanphamDao;
import com.example.nhom10_duan1.LOPDTO.HangDTO;
import com.example.nhom10_duan1.LOPDTO.SanphamDTO;
import com.example.nhom10_duan1.R;
import com.example.nhom10_duan1.SPINERADAPTER.HangSpiner;
import com.example.nhom10_duan1.SanphamViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Frag_sanpham extends Fragment {
    RecyclerView rcl_sanpham;
    FloatingActionButton flb_sanpham;
    ArrayList<HangDTO> hanges;
    int mahang;
    HangDao hangDao;
    HangSpiner hangSpiner;
    SanphamDao dao;
    SanphamViewModel model;
    SanphamAdapter adapter;
    SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragsanpham, null);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_sanpham = (RecyclerView) view.findViewById(R.id.rcl_sanpham);
        flb_sanpham = (FloatingActionButton) view.findViewById(R.id.fbl_adsanpham);
        dao = new SanphamDao(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcl_sanpham.setLayoutManager(layoutManager);
        model = new ViewModelProvider(this).get(SanphamViewModel.class);
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<SanphamDTO>>() {
            @Override
            public void onChanged(List<SanphamDTO> saches) {
                adapter = new SanphamAdapter(getActivity(), saches, dao);
                rcl_sanpham.setAdapter(adapter);

            }
        });
        searchView = view.findViewById(R.id.id_serch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        flb_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.custom_add_sanpham, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                EditText ed_tens = (EditText) view.findViewById(R.id.tensanpham);
                Spinner spns = (Spinner) view.findViewById(R.id.spin_hang);
                EditText ed_gias = (EditText) view.findViewById(R.id.ed_gia);
                EditText ed_loai = (EditText) view.findViewById(R.id.loai);
                builder.setTitle("Thêm Hãng");
                hanges = new ArrayList<>();
                hangDao = new HangDao(getContext());
                hanges = (ArrayList<HangDTO>) hangDao.GETLS();
                hangSpiner = new HangSpiner(getActivity(), hanges);
                spns.setAdapter(hangSpiner);
                spns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mahang = hanges.get(position).getMaHang();
                        Toast.makeText(getContext(), "Chọn " + hanges.get(position).getTenHang(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ed_tens.getText().length() == 0 || ed_gias.getText().length() == 0 || ed_gias.getText().length() == 0) {
                            Toast.makeText(getContext(), "Bạn cần phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int giaThue = Integer.parseInt(ed_gias.getText().toString());
                                SanphamDTO sanphamDTO = new SanphamDTO();
                                sanphamDTO.setTensanpham(ed_tens.getText().toString());
                                sanphamDTO.setLoai(ed_loai.getText().toString());
                                sanphamDTO.setGiasanpham(giaThue);
                                sanphamDTO.setMahang(mahang);
                                long kq = dao.ADDS(sanphamDTO);
                                if (kq > 0) {
                                    Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    ed_tens.setText("");
                                    ed_gias.setText("");
                                    ed_loai.setText("");
                                    spns.setSelection(0);
                                    model.getLiveData();
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "Thêm sản phẩm bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Giá  phải là số", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
