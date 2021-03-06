package com.hrms.applicationhrms.business.concretes;

import com.hrms.applicationhrms.business.abstracts.EmployerPendingApprovalService;
import com.hrms.applicationhrms.business.abstracts.EmployerService;
import com.hrms.applicationhrms.business.abstracts.SystemPersonnelConfirmationService;
import com.hrms.applicationhrms.business.abstracts.SystemPersonnelService;
import com.hrms.applicationhrms.business.constants.Messages;
import com.hrms.applicationhrms.core.utilities.results.DataResult;
import com.hrms.applicationhrms.core.utilities.results.ErrorResult;
import com.hrms.applicationhrms.core.utilities.results.Result;
import com.hrms.applicationhrms.core.utilities.results.SuccessResult;
import com.hrms.applicationhrms.entities.concretes.Employer;
import com.hrms.applicationhrms.entities.concretes.SystemPersonnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPersonnelManager implements SystemPersonnelService {

    private SystemPersonnelConfirmationService systemPersonnelConfirmationService;
    private EmployerService employerService;
    private EmployerPendingApprovalService employerPendingApprovalService;
    private PostService postService;

    @Autowired
    public SystemPersonnelManager(SystemPersonnelConfirmationService systemPersonnelConfirmationService,
                                  EmployerService employerService,EmployerPendingApprovalService employerPendingApprovalService,
                                  PostService postService) {
        this.systemPersonnelConfirmationService = systemPersonnelConfirmationService;
        this.employerService = employerService;
        this.employerPendingApprovalService = employerPendingApprovalService;
        this.postService = postService;
    }

    @Override
    public DataResult<List<SystemPersonnel>> getAll() {
        return null;
    }

    @Override
    public DataResult<SystemPersonnel> getById(int id) {
        return null;
    }

    @Override
    public Result add(SystemPersonnel entity) {
        return null;
    }

    @Override
    public Result update(SystemPersonnel entity) {
        return null;
    }

    @Override
    public Result delete(SystemPersonnel entity) {
        return null;
    }

    @Override
    public Result confirmEmployerRegistration(int employerId,int personnelId) {
        var employerPendingApproval = employerPendingApprovalService.getById(employerId).getData();
        var employer = new Employer();
        employer.setWebSite(employerPendingApproval.getWebSite());
        employer.setPhoneNumber(employerPendingApproval.getPhoneNumber());
        employer.setCompanyName(employerPendingApproval.getCompanyName());
        employer.setEmail(employerPendingApproval.getEmail());
        employer.setPassword(employerPendingApproval.getPassword());
        employer.setConfirmed(true);
        var result = employerService.add(employer);
        if(!result.isSuccess()){
            return new ErrorResult();
        }
        this.employerPendingApprovalService.delete(employerPendingApproval);
        return new SuccessResult(Messages.approved(employer.getCompanyName()));
    }

    @Override
    public Result rejectEmployerRegistration(int employerId) {
        var employerPendingApproval = employerPendingApprovalService.getById(employerId).getData();
        this.employerPendingApprovalService.delete(employerPendingApproval);
        return new SuccessResult(Messages.reject(employerPendingApproval.getCompanyName()));
    }

    @Override
    public Result activePost(int postId) {
        var post = this.postService.getById(postId).getData();
        post.setStatus(PostStatus.values()[2]);
        var result = this.postService.update(post);
        if(!result.isSuccess()){
          return new ErrorResult(Messages.activeError());
        }
        return new SuccessResult(Messages.activatedPost());
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

    @Override
    public Result rejectPost(int postId) {
        var post = this.postService.getById(postId).getData();
        post.setStatus(PostStatus.values()[1]);
        var result = this.postService.update(post);
        if(!result.isSuccess()){
            return new ErrorResult(Messages.rejectError());
        }
        return new SuccessResult(Messages.rejectPost());
    }
}
