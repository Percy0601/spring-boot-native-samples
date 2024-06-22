package io.samples.mybatis.graalvm;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeSerialization;

import io.samples.mybatis.MybatisBootstrap;

/**
 * @author: baoxin.zhao
 * @date: 2024/6/22
 */
public class LambdaRegistrationFeature implements Feature {

    @Override
    public void duringSetup(DuringSetupAccess access) {
        RuntimeSerialization.registerLambdaCapturingClass(MybatisBootstrap.class);
    }

}
