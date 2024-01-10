package com.campusdual.ws.core.rest;


import com.campusdual.api.core.service.ISignUpService;
import com.ontimize.jee.server.rest.ORestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signUps")
public class SignUpController extends ORestController<ISignUpService> {

    @Autowired
    private ISignUpService signUpService;

    @Override
    public ISignUpService getService() {
        return this.signUpService;
    }
}