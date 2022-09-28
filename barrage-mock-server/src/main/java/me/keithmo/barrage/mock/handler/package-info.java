/**
 * 同包下几个类覆盖了 Spring 的方法，要同样打上 @NonNullApi 才能消除 IDE 在方法和参数处的以下警告：
 * Not annotated method overrides method annotated with @NonNullApi
 * Not annotated parameter overrides @NonNullApi parameter
 */
@NonNullApi
package me.keithmo.barrage.mock.handler;

import org.springframework.lang.NonNullApi;
