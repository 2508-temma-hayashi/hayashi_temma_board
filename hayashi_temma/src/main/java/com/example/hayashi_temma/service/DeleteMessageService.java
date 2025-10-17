package com.example.hayashi_temma.service;

import com.example.hayashi_temma.repository.MessageRepository;
import com.example.hayashi_temma.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMessageService {
    @Autowired
    MessageRepository messageRepository;

    //userIdはログインしているユーザのID
    public boolean deleteMessage(int messageId, int userId) {
        //投稿主のIDを突き止めるためにまず投稿のIDからMessageオブジェクトを取得する
        //Optionalという（Messageがあるかもだしないかも）という戻り値が設定されているので、.orElse(null)をつける
        //そうすると、なかった場合nullが入る
        Message message = messageRepository.findById(messageId).orElse(null);

        if(message.getUser().getId() == userId){
            messageRepository.deleteById(messageId);
            return true;
        }else if(message.getUser().getId() != userId){
            return false;
        }
        //elseのほうがいいかな？
        return false;
    }

}

