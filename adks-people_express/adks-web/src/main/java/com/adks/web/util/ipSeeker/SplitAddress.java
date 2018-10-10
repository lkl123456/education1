package com.adks.web.util.ipSeeker;

import java.io.File;

/**
 * 
 * ClassName SplitAddress
 * @Description：地区分隔获取
 * @author xrl
 * @Date 2017年6月27日
 */
public class SplitAddress {  
  /* public static void main(String[] args){  
        try {  
            IPSeeker ipseeker = new IPSeeker(new File("E:/workspace/adks-people_express/adks-web/src/main/webapp/WEB-INF/ipdata/ipdata.dat"));  
            IPEntity ipentity = new IPEntity();  
            String ipaddress = "218.70.30.178";  
            SplitAddress splitaddress = new SplitAddress();  
            splitaddress.SplitAddressAction(ipaddress, ipseeker, ipentity); //切分获得多级地址  
              
            System.out.println("完整ip信息："+ipseeker.getAddress(ipaddress));  
            System.out.println("完全地址:省/市/区:"+ipseeker.getCountry(ipaddress));  
            System.out.println("nation:"+ipentity.getNation());  
            System.out.println("province:"+ipentity.getProvince());  
            System.out.println("city:"+ipentity.getCity());  
            System.out.println("region:"+ipentity.getRegion());  
            System.out.println("使用的网络(运营商ISP):"+ipseeker.getIsp(ipaddress));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
    }*/
      
    public void SplitAddressAction(String ipaddress, IPSeeker ipseeker, IPEntity ipentity){  
        try {             
            String alladdress = ipseeker.getCountry(ipaddress);   
            String[] part;  
            //全国省市里唯一没有"市"字样的只有这4个省,直接作逗号","切分  
            if(alladdress.startsWith("新疆")){  
                ipentity.setProvince("新疆");  
                alladdress = alladdress.replace("新疆", "新疆,");  
            }  
            else if(alladdress.startsWith("西藏")){  
                ipentity.setProvince("西藏");  
                alladdress = alladdress.replace("西藏", "西藏,");  
            }  
            else if(alladdress.startsWith("内蒙古")){  
                ipentity.setProvince("内蒙古");  
                alladdress = alladdress.replace("内蒙古", "内蒙古,");  
            }  
            else if(alladdress.startsWith("宁夏")){  
                ipentity.setProvince("宁夏");  
                alladdress = alladdress.replace("宁夏", "宁夏,");  
            }  
            alladdress = alladdress.replaceAll("省", "省,").replaceAll("市", "市,"); //最多切成3段:辽宁省,盘锦市,双台子区;  
            part = alladdress.split(",");  
              
            if(part.length==1){  
                //只有1级地址  
                ipentity.setNation(part[0]);  
                ipentity.setProvince(part[0]);  
            }  
            else if(part.length==2){  
                //有2级地址  
                ipentity.setProvince(part[0]);  
                ipentity.setCity(part[1]);  
            }  
            else if(part.length==3){  
                //有3级地址  
                ipentity.setProvince(part[0]);  
                ipentity.setCity(part[1]);  
                ipentity.setRegion(part[2]);  
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
}  
