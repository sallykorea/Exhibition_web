package com.acorn.exhibition.users.dto;

import org.springframework.web.multipart.MultipartFile;

public class UsersDto {
      
   private String id;
   private String name;
   private String pwd;
   private String email;
   private String birth;
   private String profile;
   private String gender;
   private String newPwd;
   private MultipartFile uploadProfile;
   private String admin;
   private String savePath;

   public UsersDto() {}

   public UsersDto(String id, String name, String pwd, String email, String birth, String profile, String gender,
         String newPwd, MultipartFile uploadProfile, String admin, String savePath) {
      super();
      this.id = id;
      this.name = name;
      this.pwd = pwd;
      this.email = email;
      this.birth = birth;
      this.profile = profile;
      this.gender = gender;
      this.newPwd = newPwd;
      this.uploadProfile = uploadProfile;
      this.admin = admin;
      this.savePath = savePath;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPwd() {
      return pwd;
   }

   public void setPwd(String pwd) {
      this.pwd = pwd;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getBirth() {
      return birth;
   }

   public void setBirth(String birth) {
      this.birth = birth;
   }

   public String getProfile() {
      return profile;
   }

   public void setProfile(String profile) {
      this.profile = profile;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public String getNewPwd() {
      return newPwd;
   }

   public void setNewPwd(String newPwd) {
      this.newPwd = newPwd;
   }

   public MultipartFile getUploadProfile() {
      return uploadProfile;
   }

   public void setUploadProfile(MultipartFile uploadProfile) {
      this.uploadProfile = uploadProfile;
   }

   public String getAdmin() {
      return admin;
   }

   public void setAdmin(String admin) {
      this.admin = admin;
   }

   public String getSavePath() {
      return savePath;
   }

   public void setSavePath(String savePath) {
      this.savePath = savePath;
   }


}