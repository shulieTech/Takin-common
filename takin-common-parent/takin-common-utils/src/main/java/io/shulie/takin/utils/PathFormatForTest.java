package io.shulie.takin.utils;

/**
 * @author pnz.zhao
 * @Description:
 * @date 2022/3/8 10:10
 */
public class PathFormatForTest {
    /**
     * 本地文件目录配置，改动点太多；而且有些在文件中，有些在数据库中配置，好乱
     * 为了便于本地测试，暂用路径重新格式化方式改动
     * @param path
     * @return
     */
    public static String format(String path) {
        boolean isLocEnv;
        String os = System.getProperties().getProperty("os.name").toLowerCase();
        if (os.contains("win") || os.contains("mac")) {
            isLocEnv = true;
        } else {
            isLocEnv = false;
        }
        if(isLocEnv) {
            if (!path.replace("//", "/").startsWith("/Users/"+System.getProperty("user.name"))) {
                path = "/Users/"+System.getProperty("user.name") +(!path.contains("data")?"/data":"") + path.replace("/nfs_dir", "");
            }else{
                path = path.replace("/nfs_dir", "");
            }
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(format("a"));
    }
}
