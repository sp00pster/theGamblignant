package theGamblignant.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.actions.RollNumberEffect;
import theGamblignant.actions.TimedVFXAction;
import theGamblignant.powers.*;

import java.util.Iterator;

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

    //the "purpose" parameter is currently not used, but im keeping it in the event that i need to use it later.
    //it used to distinguish attack rolls from skill rolls
    public static int roll(int count, int faces, char purpose) {

        //counts for roll-modifying powers
        int luck = 0;
        int vim = 0;
        int curse = 0;

        int result;
        int totalResult = 0;
        AbstractPlayer p = AbstractDungeon.player;
        GameActionManager a = AbstractDungeon.actionManager;

        //check for all powers that directly modify luck, make them flash, and record their values. remove vim and reduce curse also.
        if (p.hasPower(LuckPower.POWER_ID)) {
            p.getPower(LuckPower.POWER_ID).flash();
            luck = p.getPower(LuckPower.POWER_ID).amount;
        }

        if (p.hasPower(VimPower.POWER_ID)) {
            p.getPower(VimPower.POWER_ID).flash();
            vim = p.getPower(VimPower.POWER_ID).amount;
            a.addToBottom(new RemoveSpecificPowerAction(p, p, VimPower.POWER_ID));
        }

        if (p.hasPower(CursePower.POWER_ID)) {
            p.getPower(CursePower.POWER_ID).flash();
            curse = p.getPower(CursePower.POWER_ID).amount;
            a.addToBottom(new ReducePowerAction(p, p, CursePower.POWER_ID, count));
        }

        if (p.hasRelic("Bionic Boundbeast")) {
            //flash?
            luck += 1;
        }

        logger.info("_______________ CARD START _______________");


        for (int i = 0; i < count; i++) { //everything in here is repeated for each roll called for by int count
            logger.info("____________ ROLL START ____________");

            //this is when the random number is actually selected
            result = AbstractDungeon.cardRandomRng.random(1, faces);
            logger.info("rolled a " + result + " before luck");

            //the luck powers are applied to the random number, creating the final result of the roll
            result += luck;
            result += vim;
            result -= curse;
            logger.info(luck + " luck, " + vim + " vim, and " + curse + " curse make the result a " + result + " (before being clamped)");

            //clamp result so it doesnt exceed the bounds of the roll
            if (result > faces) {result = faces;}
            if (result < 1) {result = 1;}

            //make sure vim doesnt apply to further loops, and that curse decreases by 1 for each loop
            vim = 0;
            if (curse > 0) {
                curse--;
            }

            //ancestral awakening. it just overrides whatever the roll was
            if ((faces == 8) && (p.hasPower("Ancestral Awakening"))) {
                logger.info("ancestral awakening activates");
                result = 8;
            }

            logger.info("thus, the result is " + result);

            //make the roll vfx
            if (result == faces) {
                AbstractDungeon.actionManager.addToBottom(new TimedVFXAction(new RollNumberEffect(p.dialogX + 25F, p.dialogY, result + "!")));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TimedVFXAction(new RollNumberEffect(p.dialogX + 25F, p.dialogY, Integer.toString(result))));
            }

            //trigger any effects that listen for dice rolls
            onRollEffects(result, purpose, p, a);

            totalResult += result;

            logger.info("_____________ ROLL END _____________");
        }
        logger.info("total result is " + totalResult);
        logger.info("________________ CARD END ________________");
        return totalResult;
    }

    //this function goes through and checks for every effect (powers and relics) that listens for dice rolls. split into another function for readability
    //feel free to add more params as needed. the function is only called once so its not a big issue
    private static void onRollEffects(int result, char purpose, AbstractPlayer p, GameActionManager a) {

    //POWERS
        //if player has trashtalk and rolled a 1, apply poison to all enemies (code taken from noxious fumes)
        if (p.hasPower("Trash Talk") && result == 1) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                p.getPower("Trash Talk").flash();
                Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
                while(var1.hasNext()) {
                    AbstractMonster m = (AbstractMonster)var1.next();
                    if (!m.isDead && !m.isDying) {
                        a.addToBottom(new ApplyPowerAction(
                                m, p, new PoisonPower(m, p, p.getPower("Trash Talk").amount), p.getPower("Trash Talk").amount));
                    }
                }
            }
        }



    //RELICS
        //if player has dim bulb and rolled a 1, gain 2 vim
        if (p.hasRelic("Dim Bulb") && result == 1) {
            a.addToBottom(new ApplyPowerAction(p, p, new VimPower(p, 2), 2));
        }
    }
}