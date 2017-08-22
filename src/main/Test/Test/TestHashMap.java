import java.util.HashMap;
import java.util.Map;

/**
 * Created by yexiaoxin on 2017/8/22.
 */
public class TestHashMap {

    public static void main(String args[]){
       final Map<String,String> map = new HashMap<String, String>();

        Thread thread = new Thread(){
            @Override
            public void run() {
                int i =10000;
                for(int j=0;j<1000000;j++){
                    i++;
                    map.put("test"+i, "tets");
                }
            }
        };
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                int i =0;
               for(int j=0;j<1000000;j++){
                    map.put("test"+i, "tets");
                }
            }
        };
        thread.start();
        thread1.start();
//        for(Map.Entry<String,String> entry:map.entrySet()){
//            System.out.println(entry.getKey()+":"+entry.getValue());
//        }
    }
}
