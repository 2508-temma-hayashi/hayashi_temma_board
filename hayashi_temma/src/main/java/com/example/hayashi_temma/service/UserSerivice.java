package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.UserRepository;
import com.example.hayashi_temma.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSerivice {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUsersWithBranchAndDepartment();
    }

    public void updatedStatus(int id) {

        User user = userRepository.findById(id).orElse(null);
        int status = user.getIsStopped();
        if (status == 0) {
            user.setIsStopped(1);
        }else if (status == 1) {
            user.setIsStopped(0);
        }

        userRepository.save(user);
    }


}
