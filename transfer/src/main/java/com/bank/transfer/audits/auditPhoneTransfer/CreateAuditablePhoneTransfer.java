package com.bank.transfer.audits.auditPhoneTransfer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Специальный интерфейс, содержащий кастомную аннотацию.
 * Данная аннотация служит для пометки метода как аудируемого,
 * указывая, что он должен отслеживаться для целей аудита "PhoneTransfer".
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface CreateAuditablePhoneTransfer {

}
