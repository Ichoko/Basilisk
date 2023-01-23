package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DropDownDTO {
    private Object value;
    private String text ;


    public static List<DropDownDTO> getRoleDropdown(){
        List<DropDownDTO> roles = new LinkedList<DropDownDTO>();
        roles.add(new DropDownDTO("Administrator","Administrator"));
        roles.add(new DropDownDTO("Finance","Finance"));
        roles.add(new DropDownDTO("Salesman","Salesman"));
        return roles;
    }
}
