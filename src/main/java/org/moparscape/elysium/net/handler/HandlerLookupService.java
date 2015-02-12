package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.net.codec.Message;
import org.moparscape.elysium.net.codec.decoder.message.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class HandlerLookupService {

    private static final Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers;

    static {
        ImmutableBindingBuilder bindings = new ImmutableBindingBuilder();
        try {
            // Bind all Message handlers here
            bindings.bind(AppearancesMessage.class, PlayerMiscMessageHandlers.AppearancesMessageHandler.class);
            bindings.bind(AppearanceUpdateMessage.class, PlayerMiscMessageHandlers.AppearanceUpdateMessageHandler.class);
            bindings.bind(AttackNpcMessage.class, CombatMessageHandlers.AttackNpcMessageHandler.class);
            bindings.bind(AttackPlayerMessage.class, CombatMessageHandlers.AttackPlayerMessageHandler.class);
            bindings.bind(AttackStyleMessage.class, CombatMessageHandlers.AttackStyleMessageHandler.class);
            bindings.bind(BankCloseMessage.class, BankMessageHandlers.BankCloseMessageHandler.class);
            bindings.bind(BankDepositMessage.class, BankMessageHandlers.BankDepositMessageHandler.class);
            bindings.bind(BankWithdrawMessage.class, BankMessageHandlers.BankWithdrawMessageHandler.class);
            bindings.bind(BotDetectionMessage.class, MiscMessageHandlers.BotDetectionMessageHandler.class);
            bindings.bind(CommandMessage.class, MiscMessageHandlers.CommandMessageHandler.class);
            bindings.bind(DuelAcceptMessage.class, DuelMessageHandlers.DuelAcceptMessageHandler.class);
            bindings.bind(DuelConfirmAcceptMessage.class, DuelMessageHandlers.DuelConfirmAcceptMessageHandler.class);
            bindings.bind(DuelDeclineMessage.class, DuelMessageHandlers.DuelDeclineMessageHandler.class);
            bindings.bind(DuelInformationMessage.class, DuelMessageHandlers.DuelInformationMessageHandler.class);
            bindings.bind(DuelOptionsMessage.class, DuelMessageHandlers.DuelOptionsMessageHandler.class);
            bindings.bind(DuelRequestMessage.class, DuelMessageHandlers.DuelRequestMessageHandler.class);
            bindings.bind(DummyMessage.class, DummyMessageHandler.class);
            bindings.bind(ExceptionMessage.class, MiscMessageHandlers.ExceptionMessageHandler.class);
            bindings.bind(FollowRequestMessage.class, MovementMessageHandlers.FollowRequestMessageHandler.class);
            bindings.bind(FriendAddMessage.class, CommunicationMessageHandlers.FriendAddMessageHandler.class);
            bindings.bind(FriendPmMessage.class, CommunicationMessageHandlers.FriendPmMessageHandler.class);
            bindings.bind(FriendRemoveMessage.class, CommunicationMessageHandlers.FriendRemoveMessageHandler.class);
            bindings.bind(GameSettingMessage.class, PlayerMiscMessageHandlers.GameSettingMessageHandler.class);
            bindings.bind(IgnoreAddMessage.class, CommunicationMessageHandlers.IgnoreAddMessageHandler.class);
            bindings.bind(IgnoreRemoveMessage.class, CommunicationMessageHandlers.IgnoreRemoveMessageHandler.class);
            bindings.bind(InventoryActionMessage.class, PlayerMiscMessageHandlers.InventoryActionMessageHandler.class);
            bindings.bind(ItemDoorMessage.class, ItemMessageHandlers.ItemDoorMessageHandler.class);
            bindings.bind(ItemDropMessage.class, ItemMessageHandlers.ItemDropMessageHandler.class);
            bindings.bind(ItemGameObjectMessage.class, ItemMessageHandlers.ItemGameObjectMessageHandler.class);
            bindings.bind(ItemGroundItemMessage.class, ItemMessageHandlers.ItemGroundItemMessageHandler.class);
            bindings.bind(ItemItemMessage.class, ItemMessageHandlers.ItemItemMessageHandler.class);
            bindings.bind(ItemNpcMessage.class, ItemMessageHandlers.ItemNpcMessageHandler.class);
            bindings.bind(ItemPickupMessage.class, ItemMessageHandlers.ItemPickupMessageHandler.class);
            bindings.bind(ItemPlayerMessage.class, ItemMessageHandlers.ItemPlayerMessageHandler.class);
            bindings.bind(ItemUnwieldMessage.class, ItemMessageHandlers.ItemUnwieldMessageHandler.class);
            bindings.bind(ItemWieldMessage.class, ItemMessageHandlers.ItemWieldMessageHandler.class);
            bindings.bind(LoginMessage.class, SessionMessageHandlers.LoginMessageHandler.class);
            bindings.bind(LogoutMessage.class, SessionMessageHandlers.LogoutMessageHandler.class);
            bindings.bind(LogoutRequestMessage.class, SessionMessageHandlers.LogoutRequestMessageHandler.class);
            bindings.bind(MenuActionMessage.class, PlayerMiscMessageHandlers.MenuActionMessageHandler.class);
            bindings.bind(NpcActionMessage.class, NpcMessageHandlers.NpcActionMessageHandler.class);
            bindings.bind(NpcChatMessage.class, NpcMessageHandlers.NpcChatMessageHandler.class);
            bindings.bind(ObjectPrimaryActionMessage.class, WorldMessageHandlers.ObjectPrimaryActionMessageHandler.class);
            bindings.bind(ObjectSecondaryActionMessage.class, WorldMessageHandlers.ObjectSecondaryActionMessageHandler.class);
            bindings.bind(PingMessage.class, MiscMessageHandlers.PingMessageHandler.class);
            bindings.bind(PrayerActivateMessage.class, CombatMessageHandlers.PrayerActivateMessageHandler.class);
            bindings.bind(PrayerDeactivateMessage.class, CombatMessageHandlers.PrayerDeactivateMessageHandler.class);
            bindings.bind(PrivacySettingMessage.class, CommunicationMessageHandlers.PrivacySettingMessageHandler.class);
            bindings.bind(PublicChatMessage.class, CommunicationMessageHandlers.PublicChatMessageHandler.class);
            bindings.bind(ReportMessage.class, MiscMessageHandlers.ReportMessageHandler.class);
            bindings.bind(SessionRequestMessage.class, SessionMessageHandlers.SessionRequestMessageHandler.class);
            bindings.bind(ShopBuyMessage.class, ShopMessageHandlers.ShopBuyMessageHandler.class);
            bindings.bind(ShopCloseMessage.class, ShopMessageHandlers.ShopCloseMessageHandler.class);
            bindings.bind(ShopSellMessage.class, ShopMessageHandlers.ShopSellMessageHandler.class);
            bindings.bind(SleepwordMessage.class, MiscMessageHandlers.SleepwordMessageHandler.class);
            bindings.bind(SpellDoorMessage.class, SpellMessageHandlers.SpellDoorMessageHandler.class);
            bindings.bind(SpellGameObjectMessage.class, SpellMessageHandlers.SpellGameObjectMessageHandler.class);
            bindings.bind(SpellGroundItemMessage.class, SpellMessageHandlers.SpellGroundItemMessageHandler.class);
            bindings.bind(SpellGroundMessage.class, SpellMessageHandlers.SpellGroundMessageHandler.class);
            bindings.bind(SpellInvItemMessage.class, SpellMessageHandlers.SpellInvItemMessageHandler.class);
            bindings.bind(SpellNpcMessage.class, SpellMessageHandlers.SpellNpcMessageHandler.class);
            bindings.bind(SpellPlayerMessage.class, SpellMessageHandlers.SpellPlayerMessageHandler.class);
            bindings.bind(SpellSelfMessage.class, SpellMessageHandlers.SpellSelfMessageHandler.class);
            bindings.bind(TradeAcceptMessage.class, TradeMessageHandlers.TradeAcceptMessageHandler.class);
            bindings.bind(TradeConfirmAcceptMessage.class, TradeMessageHandlers.TradeConfirmAcceptMessageHandler.class);
            bindings.bind(TradeDeclineMessage.class, TradeMessageHandlers.TradeDeclineMessageHandler.class);
            bindings.bind(TradeInformationMessage.class, TradeMessageHandlers.TradeInformationMessageHandler.class);
            bindings.bind(TradeRequestMessage.class, TradeMessageHandlers.TradeRequestMessageHandler.class);
            bindings.bind(TrapMessage.class, MiscMessageHandlers.TrapMessageHandler.class);
            bindings.bind(WalkMessage.class, MovementMessageHandlers.WalkMessageHandler.class);
            bindings.bind(WalkTargetMessage.class, MovementMessageHandlers.WalkTargetMessageHandler.class);
            bindings.bind(WallObjectPrimaryActionMessage.class, WorldMessageHandlers.WallObjectPrimaryActionMessageHandler.class);
            bindings.bind(WallObjectSecondaryActionMessage.class, WorldMessageHandlers.WallObjectSecondaryActionMessageHandler.class);

            // Assign an immutable copy to the class' handler map
            handlers = bindings.messageToHandlerMap();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Message> MessageHandler<T> getHandler(Class<T> type) {
        return (MessageHandler<T>) handlers.get(type);
    }

    private static final class ImmutableBindingBuilder {

        private final Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers =
                new HashMap<Class<? extends Message>, MessageHandler<? extends Message>>(100, 0.50f);

        public <T extends Message> void bind(Class<T> type, Class<? extends MessageHandler<T>> handlerType)
                throws IllegalAccessException, InstantiationException {
            MessageHandler<T> handler = handlerType.newInstance();
            handlers.put(type, handler);
        }

        public Map<Class<? extends Message>, MessageHandler<? extends Message>> messageToHandlerMap() {
            return Collections.unmodifiableMap(handlers);
        }
    }
}
