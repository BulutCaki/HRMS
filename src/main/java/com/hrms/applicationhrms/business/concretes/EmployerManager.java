package com.hrms.applicationhrms.business.concretes;

import com.hrms.applicationhrms.business.abstracts.EmployerPendingApprovalService;
import com.hrms.applicationhrms.business.abstracts.EmployerService;
import com.hrms.applicationhrms.business.constants.Messages;
import com.hrms.applicationhrms.core.utilities.helpers.EmailService;
import com.hrms.applicationhrms.core.utilities.results.*;
import com.hrms.applicationhrms.dataAccess.abstracts.EmployerDao;
import com.hrms.applicationhrms.entities.concretes.Employer;
import com.hrms.applicationhrms.entities.concretes.EmployerPendingApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployerManager implements EmployerService {

    private EmployerDao employerDao;
    private EmailService emailService;
    private EmployerPendingApprovalService employerPendingApprovalService;

    @Autowired
    public EmployerManager(EmployerDao employerDao,EmailService emailService,
                           EmployerPendingApprovalService employerPendingApprovalService) {
        this.employerDao = employerDao;
        this.emailService = emailService;
        this.employerPendingApprovalService = employerPendingApprovalService;
    }

    @Override
    public DataResult<List<Employer>> getAll() {
        return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),Messages.employersListed());
    }

    @Override
    public DataResult<Employer> getById(int id) {
        return new SuccessDataResult<Employer>(this.employerDao.findById(id).get());
    }

    @Override
    public Result add(Employer entity) {
        this.employerDao.save(entity);
        return new SuccessResult();
    }

    @Override
    public Result update(Employer entity) {
        return null;
    }

    @Override
    public Result delete(Employer entity) {
        return null;
    }

    @Override
    public boolean isVerified() {
        return true;
    }

    @Override
    public Result checkEmailConfirm(EmployerPendingApproval entity){
        this.emailService.send(entity.getEmail(),"Do??rulama Kodu", UUID.randomUUID().toString());
        if (!this.isVerified()){
            return new ErrorResult();
        }
        this.employerPendingApprovalService.save(entity);
        return new SuccessResult();
    }
    @Override
    public Result passivePost(int postId) {
        var post = this.postService.getById(postId).getData();
        post.setStatus(PostStatus.values()[3]);
        var result = this.postService.update(post);
        if(!result.isSuccess()){
            return new ErrorResult(Messages.passiveError());
        }
        return new SuccessResult(Messages.passivePost());
    }
}
