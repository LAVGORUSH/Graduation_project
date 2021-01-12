package com.lavgorush.graduation_project.votingsystem.to;

import com.lavgorush.graduation_project.votingsystem.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BaseTo implements HasId {
    protected Integer id;
}
