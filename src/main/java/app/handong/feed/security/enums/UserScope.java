package app.handong.feed.security.enums;


import lombok.Getter;

import java.util.Set;

public class UserScope {

    public enum ScopeAction {
        READ,
        WRITE,
        DELETE,
    }

    @Getter
    public enum ScopeGroup {
        MEMBER(Set.of(ScopeAction.READ, ScopeAction.WRITE)),
        FEED(Set.of(ScopeAction.READ)),
        FILE(Set.of(ScopeAction.READ)),
        APIKEY(Set.of(ScopeAction.READ, ScopeAction.WRITE, ScopeAction.DELETE)),
        TAG(Set.of(ScopeAction.READ, ScopeAction.WRITE, ScopeAction.DELETE)),
        TAG_ASSIGN(Set.of(ScopeAction.WRITE, ScopeAction.DELETE));

        private final Set<ScopeAction> allowedActions;

        ScopeGroup(Set<ScopeAction> allowedActions) {
            this.allowedActions = allowedActions;
        }

        public boolean supports(ScopeAction action) {
            return allowedActions.contains(action);
        }

    }

    public record Scope(ScopeGroup group, ScopeAction action) {
        @Override
        public String toString() {
            return group.name().toLowerCase() + ":" + action.name().toLowerCase();
        }
    }
}