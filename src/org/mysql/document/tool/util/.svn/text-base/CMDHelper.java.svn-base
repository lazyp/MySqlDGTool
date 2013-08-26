package org.mysql.document.tool.util;

import java.io.File;

import org.mysql.document.tool.Parameters;

/**
 * 帮助类
 * 
 * @author hxl
 * @date 2012-6-14下午4:34:57
 */
public final class CMDHelper {
    private static final String cmdHelpString =
	    "-h 数据库地址 \n"+
	    "-u 用户名 \n"+
	    "-p 密码 \n"+
	    "-P 端口 \n"+
	    "-d 数据库名 \n"+
	    "-t 表名 \n"+
	    "--path 储存文档的路径\n"+
	    "--help 帮助 \n"+
	    "说明:\n" +
	    "  1.-u -p -d 必填参数.\n"+
	    "  2.命令后面要打一个空格，然后跟参数 \n"+
	    "  如：-h 127.0.0.1 -u root -p root -d database --path D:\\doc \n"
	    ;
    
    /**
     * 打印帮助信息
     */
    public static void printCmdHelp(){
	System.out.println(cmdHelpString);
    }
    
    /**
     * 命令参数解析
     * @param cmd
     * @return
     */
    public static Parameters parseCommand(String cmd){
	Parameters parameter = new Parameters();
	String[] cmds = cmd.split(" ");
	
	if(cmds.length==1 && cmds[0].equals("--help")){
	    CMDHelper.printCmdHelp();
	    return null;
	}
	
	if (cmds.length < 6) {
	    System.out.println("参数不全.");
	    CMDHelper.printCmdHelp();
	   // System.exit(-1);
	    return null;
	}

	// 分析参数
	int len = cmds.length;
	for (int i = 0; i < len; i += 2) {
	    if (cmds[i].equals("-u")) {
		parameter.setUser(cmds[i + 1]);
	    } else if (cmds[i].equals("-p")) {
		parameter.setPassword(cmds[i + 1]);
	    } else if (cmds[i].equals("-d")) {
		parameter.setDatabase(cmds[i + 1]);
	    } else if (cmds[i].equals("-t")) {
		parameter.setTable(cmds[i + 1]);
	    } else if (cmds[i].equals("-h")) {
		parameter.setHost(cmds[i + 1]);
	    } else if (cmds[i].equals("-P")) {
		parameter.setPort(cmds[i + 1]);
	    } else if(cmds[i].equals("--path")){
		parameter.setPath(resolvePath(cmds[i+1]));
	    }else {
		System.err.println(cmds[i] + "is Illegal parameters!");
		CMDHelper.printCmdHelp();
		//System.exit(-1);
		return null;
	    }
	}
	return parameter;
    }
    
    /**
     * 路径处理
     * @param path
     * @return
     */
    private  static String resolvePath(String path){
	String separator = File.separator;
	if(!path.endsWith(separator)){ 
	   path += separator;
	}
	return path;
    }

}
