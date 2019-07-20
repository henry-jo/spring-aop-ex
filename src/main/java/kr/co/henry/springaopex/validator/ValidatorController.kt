package kr.co.henry.springaopex.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.SmartValidator
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/validator")
class ValidatorController {

    @Autowired
    private lateinit var messageSource: MessageSource

    @Autowired
    private lateinit var validator: SmartValidator

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(ex: BindException): ResponseEntity<Map<String, String>> {
        val errors = ex.fieldErrors.map { fieldError -> fieldError.field to messageSource.getMessage(fieldError, null) }.toMap()
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/sign-up/step1")
    fun validatorSignUpStep1(
        @Valid @RequestBody step1: SignUpStep1,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {
        if (bindingResult.hasErrors()) {
            throw BindException(bindingResult)
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping("/sign-up/step2")
    fun validatorSignUpStep2(
        @Valid @RequestBody step2: SignUpStep2,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {
        if (bindingResult.hasErrors()) {
            throw BindException(bindingResult)
        }

        val groups = mutableListOf<Class<*>?>()

        step2.man?.let {
            if (it) {
                groups.add(SignUpStep2.Man::class.java)
            }
        }

        validator.validate(step2, bindingResult, *groups.toTypedArray())

        if (bindingResult.hasErrors()) {
            throw BindException(bindingResult)
        }

        return ResponseEntity.ok().build()
    }

    @PostMapping("/sign-up")
    fun validatorSignUp(
        @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<Unit> {
        val step1Result = BeanPropertyBindingResult(signUpRequest.step1, "step1")
        validator.validate(signUpRequest.step1, step1Result)

        if (step1Result.hasErrors()) {
            throw BindException(step1Result)
        }

        val step2Result = BeanPropertyBindingResult(signUpRequest.step2, "step2")

        val groups = mutableListOf<Class<*>?>()

        signUpRequest.step2.man?.let {
            if (it) {
                groups.add(SignUpStep2.Man::class.java)
            }
        }

        validator.validate(signUpRequest.step2, step2Result, *groups.toTypedArray())

        if (step2Result.hasErrors()) {
            throw BindException(step2Result)
        }

        return ResponseEntity.ok().build()
    }
}