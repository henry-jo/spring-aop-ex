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

@RestController
@RequestMapping("/validator/enhance")
class ValidatorEnhanceController {

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
        @RequestBody step1: ValidEhStep1,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {
        validate(step1, bindingResult)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/sign-up/step2")
    fun validatorSignUpStep2(
        @RequestBody step2: ValidEhStep2,
        bindingResult: BindingResult
    ): ResponseEntity<Unit> {
        validate(step2, bindingResult)

        return ResponseEntity.ok().build()
    }

    @PostMapping("/sign-up")
    fun validatorSignUp(
        @RequestBody signUpRequest: ValidRequest
    ): ResponseEntity<Unit> {
        val step1Result = BeanPropertyBindingResult(signUpRequest.step1, "step1")
        validate(signUpRequest.step1, step1Result)

        val step2Result = BeanPropertyBindingResult(signUpRequest.step2, "step2")
        validate(signUpRequest.step2, step2Result)

        return ResponseEntity.ok().build()
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(BindException::class)
    private fun validate(request: ApiValidator, bindingResult: BindingResult) {
        val validationGroups = request.validationGroups()

        validator.validate(request, bindingResult, *validationGroups as Array<Any>)

        if (bindingResult.hasErrors()) {
            throw BindException(bindingResult)
        }
    }
}