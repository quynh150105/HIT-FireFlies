package com.example.hit_networking_base.domain.dto.request;

import com.example.hit_networking_base.constant.Gender;
import com.example.hit_networking_base.constant.Role;
import com.example.hit_networking_base.domain.entity.*;
import com.example.hit_networking_base.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCreateUserDTO {
    @NotBlank(message="Hay nhap userName")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String username;

    @NotBlank(message="Hay nhap Pass Word")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message="Mat khau it nhat co 1 chu thuong, 1 chu hoa, 1 so, 1 ki tu dac biet, it nhat 8 ki tu")
    private String passwordHash;


    private Role role = Role.TV;

    private Gender gender = Gender.MALE;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải trước ngày hôm nay")
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message="Hay nhap ho va ten")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String fullName;

    @Email(message="Hay nhap email dung dinh dang")
    @NotBlank(message = "email khong duoc de trong")
    private String email;

    private LocalDate createdAt = LocalDate.now();

    private LocalDate deletedAt;

    private List<CV> cvs;

    private List<Comment> comments;


    private List<Reaction> reactions;


    private List<Event> createdEvents;


    private List<JobPost> createdJobPosts;


}
