package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Channel.AddChannelRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.ChannelStatusRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.GetChannelsRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.UpdateChannelRequest;
import com.americadigital.libupapi.WsPojos.Response.Channels.ChannelListResponse;
import com.americadigital.libupapi.WsPojos.Response.Channels.GetChannelResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;

public interface ChannelsBusiness {
    ResponseEntity<HeaderResponse> createChannel(AddChannelRequest request);

    ResponseEntity<HeaderResponse> updateChannel(UpdateChannelRequest updateChannelRequest);

    ResponseEntity<HeaderResponse> changeStatus(ChannelStatusRequest channelStatusRequest);

    ResponseEntity<GetChannelResponse> getChannelById(String idChannel);

    ResponseEntity<ChannelListResponse> getChannelsForPanel(GetChannelsRequest request);

    ResponseEntity<ChannelListResponse> getChannelsActives();

    ResponseEntity<HeaderResponse> deleteChannel(String idChannel);
}
