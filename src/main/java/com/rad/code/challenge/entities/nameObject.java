package com.rad.code.challenge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class nameObject {
    String common;
    String official;
    HashMap<String,HashMap<String,String>> nativeName;

    //Default constructor
    public nameObject(){
    }

    //constructor that receives a JsonNode and breaks it down according to the fields.
    public nameObject(JsonNode countryInfo) throws JsonProcessingException {
        try {
            JsonNode nameInfo = countryInfo.get("name");
            // If the country does not have a nativeName - skip this part

            if(nameInfo.get("common")!=null) {
                this.common = nameInfo.get("common").asText();
            }
            // If the country does not have a nativeName - skip this part

            if(nameInfo.get("official")!=null) {
                this.official = nameInfo.get("official").asText();
            }
            // If the country does not have a nativeName - skip this part
            if(nameInfo.get("nativeName")!=null){
                String native_name_language = nameInfo.get("nativeName").fieldNames().next();
                JsonNode native_name_Info = nameInfo.get("nativeName").get(native_name_language);
                this.nativeName = new HashMap<>();
                this.nativeName.put(native_name_language, getJsonNodeField(native_name_Info));
            }
        }
        catch(Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param jsonNode - native Name node info
     * @return Returns a HashMap containing the local and official name
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    public HashMap<String,String> getJsonNodeField(JsonNode jsonNode) throws JsonMappingException, JsonProcessingException {
        try{
            HashMap<String,String> dic = new HashMap<>();
            dic.put("official",jsonNode.get("official").asText());
            dic.put("common",jsonNode.get("common").asText());
            return dic;
        }
        catch(Exception e) {
            throw e;
        }
    }
}


