/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.geyser.entity.type.living;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.geysermc.geyser.entity.GeyserEntityDefinition;
import org.geysermc.geyser.inventory.GeyserItemStack;
import org.geysermc.geyser.item.Items;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.util.InteractionResult;
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.Hand;

import java.util.UUID;

public class IronGolemEntity extends GolemEntity {

    public IronGolemEntity(GeyserSession session, int entityId, long geyserId, UUID uuid, GeyserEntityDefinition<?> definition, Vector3f position, Vector3f motion, float yaw, float pitch, float headYaw) {
        super(session, entityId, geyserId, uuid, definition, position, motion, yaw, pitch, headYaw);
        // Indicate that we should show cracks through a resource pack
        setFlag(EntityFlag.BRIBED, true);
        // Required, or else the overlay is black
        dirtyMetadata.put(EntityDataTypes.COLOR_2, (byte) 0);
        // Default max health. Ensures correct cracked texture is used
        // Bug reproducible in 1.19.0 JE vanilla/fabric when spawning a new iron golem
        maxHealth = 100f;
    }

    @NonNull
    @Override
    protected InteractionResult mobInteract(@NonNull Hand hand, @NonNull GeyserItemStack itemInHand) {
        if (itemInHand.asItem() == Items.IRON_INGOT) {
            if (health < maxHealth) {
                // Healing the iron golem
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
        return super.mobInteract(hand, itemInHand);
    }
}
