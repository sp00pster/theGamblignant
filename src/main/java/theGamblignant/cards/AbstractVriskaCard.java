package theGamblignant.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.actions.RollNumberEffect;
import theGamblignant.actions.TimedVFXAction;
import theGamblignant.powers.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractVriskaCard extends CustomCard {

        public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually

    public AbstractVriskaCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
    }

    //this roll function was inspired by that of Downfall's Snecko

    public static int roll(int faces, char purpose) {
        //purpose is used to store information about the card played. this was once used for powers that only buff attack rolls or skill rolls, but these were removed. keeping the parameter just in case tho
        int luckAmt = 0;
        int result;
        int max;
        int min;

        if (AbstractDungeon.player.hasPower(LuckPower.POWER_ID)) {
            AbstractDungeon.player.getPower(LuckPower.POWER_ID).flash();
            luckAmt += AbstractDungeon.player.getPower(LuckPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(VimPower.POWER_ID) && purpose != 'r') { //"r" stands for "repeat", where one card rolls multiple times. i couldnt find a way to make this work properly, so i am just going to manually deny the use of vim for these further rolls
            luckAmt += AbstractDungeon.player.getPower(VimPower.POWER_ID).amount;
            AbstractDungeon.player.getPower(VimPower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VimPower.POWER_ID));
            logger.info("vim triggered");
        }
        if (AbstractDungeon.player.hasPower(CursePower.POWER_ID)) {
            luckAmt -= AbstractDungeon.player.getPower(CursePower.POWER_ID).amount;
            AbstractDungeon.player.getPower(CursePower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, CursePower.POWER_ID));
        }

        logger.info("luck going into this roll: "+luckAmt); //you can remove this eventually

        max = faces;
        min = 1;
        result = AbstractDungeon.cardRandomRng.random(min, max);

        logger.info("rolled a "+result+" before luck");

        result += luckAmt;
        if (result > max) {result = max;}
        if (result < 1) {result = 1;}

        if ((faces == 8 && AbstractDungeon.player.hasPower(AncestralAwakeningPower.POWER_ID))) {
            result = 8;
        }

        if (result == max) {
            AbstractDungeon.actionManager.addToTop(new TimedVFXAction(new RollNumberEffect(AbstractDungeon.player.dialogX+25F, AbstractDungeon.player.dialogY, result+"!")));
        } else {
            AbstractDungeon.actionManager.addToTop(new TimedVFXAction(new RollNumberEffect(AbstractDungeon.player.dialogX+25F, AbstractDungeon.player.dialogY, Integer.toString(result))));

        }
        logger.info("final roll: "+result);

        return result;
    }
}