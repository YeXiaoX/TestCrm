import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.EmailMessageSchema;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

import java.net.URI;


/**
 * Created by yexiaoxin on 2017/8/2.
 */
public class TestExchange {
    public static void main(String[] args) throws Exception{
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
        ExchangeCredentials credentials = new WebCredentials("yexiaoxin@sogou-inc.com", "yxxsougou2016/");

        service.setCredentials(credentials);
        service.setUrl(new URI("https://mail.sogou-inc.com/EWS/Exchange.asmx"));//服务地址

        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);//收件箱

        System.out.println("未读邮件数：" + inbox.getUnreadCount());
        ItemView view = new ItemView(10);
        view.setOffset(view.getOffset());
        //view.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
        FindItemsResults<Item> findResults =service.findItems(inbox.getId(), view);
        //FindItemsResults<Item> findResults = service.findItems(inbox.getId(),new SearchFilter.IsEqualTo(EmailMessageSchema.IsRead, false), view);
        for (Item item : findResults
                .getItems()) {
            EmailMessage message = EmailMessage.bind(service, item.getId());

            System.out.println(message.getSender().toString() + ": " + item.getSubject());
            System.out.println(message.getSender().toString() + ": " + message.getBody());
//            System.out.println(item.);
        }
    }
}
