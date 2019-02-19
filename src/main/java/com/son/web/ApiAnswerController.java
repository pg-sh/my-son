package com.son.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.domain.Answer;
import com.son.domain.AnswerRepository;
import com.son.domain.Question;
import com.son.domain.QuestionRepository;
import com.son.domain.Result;
import com.son.domain.User;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("") 
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get();
		Answer answer = new Answer(loginUser, question, contents);
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public Result dalete(@PathVariable Long questionId ,@PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인 해야 합니다.");
		}
		
		Answer answer = answerRepository.findById(id).get();
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!answer.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 삭제할 수 있습니다.");
		}
		
		answerRepository.deleteById(id);
		return Result.ok();
	}
}
