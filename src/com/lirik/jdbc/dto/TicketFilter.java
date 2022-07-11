package com.lirik.jdbc.dto;

/**
 * record пришел в java_14 и автоматически сгенерит конструкторы для полей, которые пришли в круглых скобках. Реализует equals и
 * hashcode, getters, toString к полям.
 * setter не создается так как record это immutable объект и изменить его нельзя!!!
 */

public record TicketFilter(int limit,
                           int offset,
                           String passengerName,
                           String seatNo) {



}
