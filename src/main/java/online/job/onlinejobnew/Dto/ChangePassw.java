package online.job.onlinejobnew.Dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class ChangePassw {
    @NotBlank
    private UUID id;
    @NotBlank
    private String oldPassw;
    @NotBlank
    private String newPassw;

    public ChangePassw(UUID id, String oldPassw, String newPassw) {
        this.id = id;
        this.oldPassw = oldPassw;
        this.newPassw = newPassw;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOldPassw() {
        return oldPassw;
    }

    public void setOldPassw(String oldPassw) {
        this.oldPassw = oldPassw;
    }

    public String getNewPassw() {
        return newPassw;
    }

    public void setNewPassw(String newPassw) {
        this.newPassw = newPassw;
    }
}
