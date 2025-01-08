package com.kaen.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;
import com.kaen.config.redner.MyFileRender;
import com.kaen.config.session.LoserStarSession;
import com.kaen.config.session.LoserStarSessionManager;
import com.kaen.constants.DsConstans;
import com.kaen.entity.SysUser;
import com.loserstar.utils.ObjectMapConvert.LoserStarObjMapConvertUtil;
import com.loserstar.utils.cookie.CookieUtils;
import com.loserstar.utils.file.LoserStarFileUtil;

import javax.servlet.http.Cookie;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 公共Controller
 */
public abstract class BaseController extends Controller {
    /**
     * cookie存储sessionId的key，不同的容器可能不一样，需要修改
     */
    public static final String SEESION_COOKIE_KEY = PropKit.get("config.sessionCookieKey");
    /**
     * cookie存活时间
     */
    public static final int SEESION_COOKIE_MAX_AGE = PropKit.getInt("config.sessionCookieMaxAge");
    /**
     * 设置 Cookie 只在 HTTPS 下发送
     */
    public static final Boolean SEESION_COOKIE_IS_SECURE = PropKit.getBoolean("config.sessionCookieIsSecure");
    /**
     * 设置是否只通过 HTTP 协议访问 Cookie，防止 JavaScript 访问，提高安全性。
     */
    public static final Boolean SEESION_COOKIE_IS_HTTP_ONLY = PropKit.getBoolean("config.sessionCookieIsHttpOnly");

    /**
     * session管理器实现类全包路径
     */
    public static final String SESSION_MANAGER_IMPL_CLASS_PATH = PropKit.get("config.sessionManagerImplClassPackage");

    public void renderError(String msg) {
        setAttr("error", msg);
        renderFreeMarker("/500.html");
    }

    /**
     * @param path                上传目录
     * @param fileParamName       二进制文件数据的参数名称
     * @param isNewFileName       是否重命名保存到硬盘上的文件名（防止文件覆盖）
     * @param newFileName         保存到硬盘上重命名的文件名，如果不存则以uuid自动生成
     * @param isReturnNewFileName 是否返回新文件名，true的话，如果传递了newFileName就返回，如果没传递就返回自动生成的uuid
     * @return 根据isReturnNewFileName参数决定是新生成的uuid+文件后缀的新文件名，还是返回原始文件名
     * @throws Exception
     */
    @SuppressWarnings("unused")
    protected String uploadFile(String path, String fileParamName, boolean isNewFileName, String newFileName, boolean isReturnNewFileName) throws Exception {
        invalidPathCheck(path);
        String realpath = getRequest().getSession().getServletContext().getRealPath("upload"); // 获取默认上传目录，upload目录所在的绝对路径
        UploadFile uploadFile = null;
        if (fileParamName != null && !fileParamName.equals("")) {
            uploadFile = getFile(fileParamName);
        } else {
            uploadFile = getFile();
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String os = System.getProperty("os.name");
        String sourceEncode = "utf-8";
        String targetEncode = System.getProperty("file.encoding");
        if (os.equalsIgnoreCase("AIX")) {
            sourceEncode = "GBK";
        }

        String sourceFilename = uploadFile.getFileName();
        String filename = sourceFilename;//原始文件名
        if (isNewFileName) {
            if (newFileName == null || newFileName.equals("")) {
                filename = LoserStarFileUtil.generateUUIDFileName(filename);
            } else {
                filename = newFileName;
            }
        }
        path += filename;

        path = new String(path.getBytes(sourceEncode), targetEncode);
        InputStream stream = new FileInputStream(uploadFile.getFile());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
        byte[] imgBufTemp = new byte[102401];
        int length;
        while ((length = stream.read(imgBufTemp)) != -1) {
            bos.write(imgBufTemp, 0, length);
        }
        bos.flush();
        bos.close();
        stream.close();

        realpath = realpath + "/" + sourceFilename; // 拼接文件路径
        File f = new File(realpath);
        if (f.exists()) {// 删除文件
            f.delete();
        }

        if (isReturnNewFileName) {
            //返回新文件名
            return filename;
        } else {
            //返回原始文件名
            return sourceFilename;
        }
    }

    /**
     * @param path          上传目录
     * @param fileParamName 二进制文件数据的参数名称
     * @param isNewFileName 是否重命名保存到硬盘上的文件名（防止文件覆盖）
     * @return 返回新生成的文件名
     * @throws Exception
     */
    protected String uploadFile(String path, String fileParamName, boolean isNewFileName) throws Exception {
        return uploadFile(path, fileParamName, true, null, true);
    }

    /**
     * @param path          上传目录
     * @param fileParamName 二进制文件数据的参数名称
     * @param isNewFileName 是否重命名保存到硬盘上的文件名（防止文件覆盖）
     * @param newFileName   保存到硬盘上重命名的文件名，如果不存则以uuid自动生成
     * @param mRequest      通过MultipartRequest（cos组件）获取文件，不使用jfinal自带的
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    protected String uploadFile2(String path, String fileParamName, boolean isNewFileName, String newFileName, MultipartRequest mRequest) throws Exception {
        invalidPathCheck(path);
        String realpath = mRequest.getSession().getServletContext().getRealPath("upload"); // 获取默认上传目录，upload目录所在的绝对路径
        List<UploadFile> uploadFiles = mRequest.getFiles();
        UploadFile uploadFile = null;
        if (fileParamName != null && !fileParamName.equals("")) {
            for (UploadFile uploadFileTemp : uploadFiles) {
                if (uploadFile.getParameterName().equals(fileParamName)) {
                    uploadFile = uploadFileTemp;
                }
            }
        } else {
            uploadFile = uploadFiles.size() > 0 ? uploadFiles.get(0) : null;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String os = System.getProperty("os.name");
        String sourceEncode = "utf-8";
        String targetEncode = System.getProperty("file.encoding");
        if (os.equalsIgnoreCase("AIX")) {
            sourceEncode = "GBK";
        }

        String sourceFilename = uploadFile.getFileName();
        String filename = sourceFilename;
        if (isNewFileName) {
            if (newFileName == null || newFileName.equals("")) {
                filename = LoserStarFileUtil.generateUUIDFileName(filename);
            } else {
                filename = newFileName;
            }
        }
        String fileUrl = path + filename;
        path += filename;

        path = new String(path.getBytes(sourceEncode), targetEncode);
        InputStream stream = new FileInputStream(uploadFile.getFile());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
        byte[] imgBufTemp = new byte[102401];
        int length;
        while ((length = stream.read(imgBufTemp)) != -1) {
            bos.write(imgBufTemp, 0, length);
        }
        bos.flush();
        bos.close();
        stream.close();

        realpath = realpath + "/" + filename; // 拼接文件路径
        File f = new File(realpath);
        if (f.exists()) {// 删除文件
            f.delete();
        }
        return sourceFilename;
    }

    /**
     * 上传文件(基于jqwidgets的上传文件方式，确保二进制文件参数名为fileToUpload)
     *
     * @param path 上传目录
     * @return 返回原始文件名
     * @throws Exception
     */
    protected String uploadFileForJqwidgetsReturnSourceFileName(String path) throws Exception {
        return uploadFile(path, "fileToUpload", false, null, false);
    }

    /**
     * 上传文件(基于Web Uploader的上传文件方式，确保二进制文件参数名为file)
     * http://fex.baidu.com/webuploader/ https://github.com/fex-team/webuploader
     *
     * @param path        上传目录
     * @param newFileName 文件的新名称，如果不传，以uuid自动生成
     * @return 原始文件名称
     * @throws Exception
     */
    protected String uploadFileForWebUploaderForGenNewFileNamerReturnSourceFileName(String path, String newFileName) throws Exception {
        return uploadFile(path, "file", true, newFileName, false);
    }

    /**
     * 上传文件(基于Web Uploader的上传文件方式，确保二进制文件参数名为file)
     * http://fex.baidu.com/webuploader/ https://github.com/fex-team/webuploader
     *
     * @param dirPath 上传目录
     * @return 返回新的文件名
     * @throws Exception
     */
    protected String uploadFileForWebUploaderForGenNewFileNameRerturnNewFileName(String dirPath) throws Exception {
        return uploadFile(dirPath, "file", true, null, true);
    }

    /**
     * 上传文件(基于Web Uploader的上传文件方式，确保二进制文件参数名为file)
     * http://fex.baidu.com/webuploader/ https://github.com/fex-team/webuploader
     *
     * @param dirPath 上传目录
     * @return 返回新的全路径
     * @throws Exception
     */
    protected String uploadFileForWebUploaderForGenNewFileNameRerturnNewFullPath(String dirPath) throws Exception {
        String newFileName = uploadFile(dirPath, "file", true, null, true);
        return dirPath + newFileName;
    }

    /**
     * 下载问价，传入文件的储存路径
     *
     * @param path 下载的硬盘路径
     * @throws UnsupportedEncodingException
     */
    protected void downFile(String path) throws Exception {
        downFile(path, null);
    }

    /**
     * 下载文件（指定下载时显示的文件名，如不指定则使用硬盘上的原始名）
     *
     * @param path
     * @param displayFileName
     * @throws UnsupportedEncodingException
     */
    protected void downFile(String path, String displayFileName) throws Exception {
        invalidPathCheck(path);
        String os = System.getProperty("os.name");
        String sourceEncode = "utf-8";
        String targetEncode = System.getProperty("file.encoding");
        if (os.equalsIgnoreCase("AIX")) {
            sourceEncode = "GBK";
        }

        path = new String(path.getBytes(sourceEncode), targetEncode);
        File file = new File(path);
        if (!file.exists()) {
            renderHtml("文件已被删除");
        } else {
            if (displayFileName == null || displayFileName.equals("")) {
                render(new MyFileRender(file));
            } else {
                render(new MyFileRender(file, displayFileName));

            }
        }

    }

    protected Map<String, Object> toMap(Object object) throws Exception {
        return LoserStarObjMapConvertUtil.ConvertToMap(object);
    }

    protected Integer toInteger(Object object) {
        return Integer.valueOf(object.toString());
    }

    protected Double toDouble(Object object) {
        return Double.valueOf(object.toString());
    }

    protected Boolean toBoolean(Object object) {
        return Boolean.valueOf(object.toString());
    }

    protected String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * 获取分页的页码参数
     *
     * @return
     */
    protected int getPageNumber() {
        int pageNumber = getParaToInt("pageNumber", 1);
        return pageNumber;
    }

    /**
     * 获取分页的每页条数参数
     *
     * @return
     */
    protected int getPageSize() {
        int pageSize = getParaToInt("pageSize", DsConstans.PageCfg.defPageSize);
        return pageSize;
    }

    /**
     * 校验空值
     *
     * @param s
     * @return null或空字符串返回false，有值返回true
     */
    protected boolean checkNull(String s) {
        if (s == null || s.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取排序字段
     *
     * @param fieldName
     * @return
     */
    protected String getSortField(String fieldName) {
        if (checkNull(fieldName)) {
            return fieldName.substring("sort_".length(), fieldName.length());
        } else {
            return "";
        }
    }

    /**
     * 设置用户登录状态
     *
     * @param sysUser
     * @throws Exception
     */
    public void setLogin(SysUser sysUser) throws Exception {
        getLoserStarSession().setAttr("userVo", sysUser);
    }

    public void delLogin() throws Exception {
        getLoserStarSession().removeAttribute("userVo");//移除该session中的登录状态
        getLoserStarSession().destroy();//销毁session
    }

    /**
     * 获取session，根据实现来，使用那种session
     *
     * @return
     * @throws Exception
     */
    public LoserStarSession getLoserStarSession() throws Exception {
        Class<?> sessionManagerClass = Class.forName(SESSION_MANAGER_IMPL_CLASS_PATH);//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
        LoserStarSessionManager sessionManager = (LoserStarSessionManager) sessionManagerClass.newInstance();

        LoserStarSession loserStarSession = null;
        //如果变量sessionId是空的，就尝试从请求的cookie中获取sessionId
        Object sessionId = getRequest().getAttribute("sessionId");
        if (sessionId == null) {
            sessionId = CookieUtils.getCookie(getRequest(), SEESION_COOKIE_KEY);
        }
        //利用sessionId去获取seesion或者创建一个session
        loserStarSession = sessionManager.getSession(sessionId == null ? null : sessionId.toString());
        getRequest().setAttribute("sessionId", loserStarSession.getSessionId());//赋值这个线程的sessionId，以免重复生成
        //刷新cookie
        Cookie cookie = new Cookie(SEESION_COOKIE_KEY, loserStarSession.getSessionId());
        cookie.setPath("/");
        cookie.setHttpOnly(SEESION_COOKIE_IS_HTTP_ONLY);//确保 Cookie 不会被 JavaScript 访问,降低了攻击者利用 XSS 漏洞窃取用户会话信息或其他敏感数据的风险,带有 HttpOnly 标志的 Cookie 只能在 HTTP 或 HTTPS 请求中发送给服务器，而不能通过 document.cookie 等浏览器 API 读取。
        cookie.setSecure(SEESION_COOKIE_IS_SECURE);//Cookie 只能通过 HTTPS（安全的 HTTP 协议）传输，而不能通过 HTTP 传输，但是有些浏览器允许本地localhost上使用。360极速浏览器严格遵守规范，不允许本地站点使用。cookie存储不了会导致登录有问题。如本地需要使用360极速调试，需要架设本地域名代理
        cookie.setMaxAge(SEESION_COOKIE_MAX_AGE);//设置 Cookie 的最大存活时间
        getResponse().addCookie(cookie);//将cookie返回
        return loserStarSession;
    }


    /**
     * 校验非法路径字符
     *
     * @param dir
     * @throws Exception
     */
    private void invalidPathCheck(String dir) throws Exception {
        if (dir.contains("../") ||
                dir.contains("../")
                || dir.contains("./")
                || dir.contains("..")
                || dir.contains("..")
                || dir.toLowerCase().contains("%2e%2e%2f")
                || dir.contains("\\")
                || dir.toLowerCase().contains("%5c")
                || dir.toLowerCase().contains(".%2e%2f")
                || dir.toLowerCase().contains("%2e.%2f")
                || dir.toLowerCase().contains("..%2f")) {
            throw new Exception("请求错误，非法的文件路径字符");
        }
    }
}
