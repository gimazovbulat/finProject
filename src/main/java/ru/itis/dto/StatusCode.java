package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusCode {
    private Status status;
    private String descr;

    public enum Status {
        OK(0), VALIDATION_ERROR(1);

        private int statusCode;

        public int getStatusCode() {
            return statusCode;
        }

        Status(int statusCode) {
            this.statusCode = statusCode;
        }
    }
}
