package com.example.nhom10_duan1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.nhom10_duan1.LOPDAO.SanphamDao;
import com.example.nhom10_duan1.LOPDTO.SanphamDTO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SanphamViewModel extends AndroidViewModel {
    SanphamDao dao;
    MutableLiveData<List<SanphamDTO>> liveData;

    public SanphamViewModel(@NonNull @NotNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        dao = new SanphamDao(application);
    }

    public MutableLiveData<List<SanphamDTO>> getLiveData() {
        loads();
        return liveData;
    }

    private void loads() {
        List<SanphamDTO> list = new ArrayList<>();
        list = dao.GETS();
        liveData.setValue(list);
    }

//    public long addSach(Sach sach) {
//        long kq = dao.ADDS(sach);
//        if (kq > 0) {
//            loads();
//            return kq;
//        }
//        return -1;
//    }
}